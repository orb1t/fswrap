package uk.org.taverna.fswrap;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;


public class WrappedPath implements Path {

	protected Path originalPath;
	private WrappedFileSystem wrappedFileSystem;

	public WrappedFileSystem getFileSystem() {
		return wrappedFileSystem;
	}

	public boolean isAbsolute() {
		return originalPath.isAbsolute();
	}

	public WrappedPath getRoot() {
		return wrappedFileSystem.toWrappedPath(originalPath.getRoot());
	}

	public WrappedPath getFileName() {
		return wrappedFileSystem.toWrappedPath(originalPath.getFileName());
	}

	public WrappedPath getParent() {
		return wrappedFileSystem.toWrappedPath(originalPath.getParent());
	}

	public int getNameCount() {
		return originalPath.getNameCount();
	}

	public WrappedPath getName(int index) {
		return wrappedFileSystem.toWrappedPath(originalPath.getName(index));
	}

	public WrappedPath subpath(int beginIndex, int endIndex) {
		return wrappedFileSystem.toWrappedPath(originalPath.subpath(beginIndex, endIndex));
	}

	public boolean startsWith(Path other) {
		return originalPath.startsWith(wrappedFileSystem.provider().toOriginalPath(other));
	}

	public boolean startsWith(String other) {
		return originalPath.startsWith(other);
	}

	public boolean endsWith(Path other) {
		return originalPath.endsWith(other);
	}

	public boolean endsWith(String other) {
		return originalPath.endsWith(other);
	}

	public WrappedPath normalize() {
		return wrappedFileSystem.toWrappedPath(originalPath.normalize());
	}

	public WrappedPath resolve(Path other) {
		return wrappedFileSystem.toWrappedPath(originalPath.resolve(wrappedFileSystem.provider().toOriginalPath(other)));
	}

	public WrappedPath resolve(String other) {
		return wrappedFileSystem.toWrappedPath(originalPath.resolve(other));
	}

	public WrappedPath resolveSibling(Path other) {
		return wrappedFileSystem.toWrappedPath(originalPath.resolveSibling(other));
	}

	public WrappedPath resolveSibling(String other) {
		return wrappedFileSystem.toWrappedPath(originalPath.resolveSibling(other));
	}

	public WrappedPath relativize(Path other) {
		return wrappedFileSystem.toWrappedPath(originalPath.relativize(other));
	}

	public URI toUri() {
		return wrappedFileSystem.provider().toWrappedUri(originalPath.toUri());
	}

	public WrappedPath toAbsolutePath() {
		return wrappedFileSystem.toWrappedPath(originalPath.toAbsolutePath());
	}

	public WrappedPath toRealPath(LinkOption... options) throws IOException {
		return wrappedFileSystem.toWrappedPath(originalPath.toRealPath(options));
	}

	public File toFile() {
		return originalPath.toFile();
	}

	public WatchKey register(WatchService watcher, Kind<?>[] events,
			Modifier... modifiers) throws IOException {
		return originalPath.register(watcher, events, modifiers);
	}

	public WatchKey register(WatchService watcher, Kind<?>... events)
			throws IOException {
		return originalPath.register(watcher, events);
	}

	public Iterator<Path> iterator() {
		// TODO: Fix me
		return originalPath.iterator();
	}

	public int compareTo(Path other) {
		return originalPath.compareTo(wrappedFileSystem.provider().toOriginalPath(other));
	}

	public boolean equals(Object other) {
		if (! (other instanceof Path)) {
			return false;
		}
		return originalPath.equals(wrappedFileSystem.provider().toOriginalPath((Path) other));
	}

	
	public int hashCode() {
		return originalPath.hashCode();
	}

	public String toString() {
		return originalPath.toString();
	}
		
	protected WrappedPath(WrappedFileSystem wrappedFilesystem, Path originalPath) {
		this.wrappedFileSystem = wrappedFilesystem;
		this.originalPath = originalPath;
	}



}
