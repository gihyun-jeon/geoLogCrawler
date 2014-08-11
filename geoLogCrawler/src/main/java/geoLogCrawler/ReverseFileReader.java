package geoLogCrawler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReverseFileReader {
	private static final int BUFFER_SIZE = 8192;

	private final FileChannel channel;
	private final String encoding;
	private long filePosition;
	private ByteBuffer buffer;
	private int bufferPosition;
	private byte lastLineBreak = '\n';
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@SuppressWarnings("resource")
	public ReverseFileReader(File file, String encoding) throws IOException {
		RandomAccessFile targetFile = new RandomAccessFile(file, "r");
		channel = targetFile.getChannel();
		filePosition = targetFile.length();
		this.encoding = encoding;
	}

	public String readLine() throws IOException {
		while (true) {
			if (bufferPosition < 0) {
				if (filePosition == 0) {
					if (outputStream == null) {
						return null;
					}
					String line = bufToString();
					outputStream = null;
					return line;
				}

				long start = Math.max(filePosition - BUFFER_SIZE, 0);
				long end = filePosition;
				long readSize = end - start;

				buffer = channel.map(FileChannel.MapMode.READ_ONLY, start, readSize);
				bufferPosition = (int)readSize;
				filePosition = start;
			}

			while (bufferPosition-- > 0) {
				byte c = buffer.get(bufferPosition);
				if (c == '\r' || c == '\n') {
					if (c != lastLineBreak) {
						lastLineBreak = c;
						continue;
					}
					lastLineBreak = c;
					return bufToString();
				}
				outputStream.write(c);
			}
		}
	}

	private String bufToString() throws UnsupportedEncodingException {
		if (outputStream.size() == 0) {
			return "";
		}

		byte[] bytes = outputStream.toByteArray();
		for (int i = 0; i < bytes.length / 2; i++) {
			byte t = bytes[i];
			bytes[i] = bytes[bytes.length - i - 1];
			bytes[bytes.length - i - 1] = t;
		}

		outputStream.reset();

		return new String(bytes, encoding);
	}

}