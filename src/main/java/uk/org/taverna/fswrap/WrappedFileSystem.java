package uk.org.taverna.fswrap;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WrappedFileSystem extends FileSystem {

	private WrappedFileSystemProvider provider;
	private FileSystem originalFilesystem;
	private URI uri;
	private WrappedFileStore wrappedFileStore;

	public WrappedFileSystem(WrappedFileSystemProvider provider, URI uri,
			FileSystem originalFs) {
		this.provider = provider;
		this.uri = uri;
		this.originalFilesystem = originalFs;
	}

	@Override
	public WrappedFileSystemProvider provider() {
		return provider;
	}

	@Override
	public void close() throws IOException {
		// TODO: Update manifest
		originalFilesystem.close();
	}

	@Override
	public boolean isOpen() {
		return originalFilesystem.isOpen();
	}

	@Override
	public boolean isReadOnly() {
		return originalFilesystem.isReadOnly();
	}

	@Override
	public String getSeparator() {
		return originalFilesystem.getSeparator();
	}

	@Override
	public Iterable<Path> getRootDirectories() {
		return toWrappedPaths(originalFilesystem.getRootDirectories());
	}

	protected Iterable<Path> toWrappedPaths(Iterable<Path> origPaths) {
		List<Path> wrapped = new ArrayList<Path>();
		for (Path orig : origPaths) {
			// where's my yield !!??
			wrapped.add(toWrappedPath(orig));
		}
		return wrapped;
	}

	@Override
	public Iterable<FileStore> getFileStores() {
		if (wrappedFileStore == null) {
			wrappedFileStore = new WrappedFileStore(originalFilesystem.getFileStores().iterator().next());
		}
		return Collections.singleton((FileStore)wrappedFileStore);
	}

	@Override
	public Set<String> supportedFileAttributeViews() {
		return originalFilesystem.supportedFileAttributeViews();
	}

	@Override
	public Path getPath(String first, String... more) {
		Path origPath = originalFilesystem.getPath(first, more);
		return toWrappedPath(origPath);
	}
	@Override
	public PathMatcher getPathMatcher(final String syntaxAndPattern) {
		return new PathMatcher(){
			PathMatcher zipPathMatcher = originalFilesystem.getPathMatcher(syntaxAndPattern);
			@Override
			public boolean matches(Path path) {
				return zipPathMatcher.matches(provider.toOriginalPath(path));
			}
		};
	}
	
	protected WrappedPath toWrappedPath(Path origPath) {
		if (origPath == null) {
			return null;
		}
		return new WrappedPath(this, origPath);
	}

	@Override
	public UserPrincipalLookupService getUserPrincipalLookupService() {
		return originalFilesystem.getUserPrincipalLookupService();
	}

	@Override
	public WatchService newWatchService() throws IOException {
		return originalFilesystem.newWatchService();
	}


}