package com.codi.mvptest.data.cache;

import android.content.Context;

import com.codi.mvptest.data.cache.serializer.JsonSerializer;
import com.codi.mvptest.data.entity.UserEntity;
import com.codi.mvptest.data.exception.UserNotFoundException;
import com.codi.mvptest.data.executor.ThreadExecutor;

import java.io.File;

/**
 * Created by Codi on 2015/2/10.
 */
public class UserCacheImpl implements UserCache {

    private static UserCacheImpl INSTANCE;

    public static synchronized UserCacheImpl getINSTANCE(Context context, JsonSerializer userCacheSerializer, FileManager fileManager, ThreadExecutor threadExecutor) {
        if(INSTANCE == null) {
            INSTANCE = new UserCacheImpl(context, userCacheSerializer, fileManager, threadExecutor);
        }
        return INSTANCE;
    }

    private static final String SETTINGS_FILE_NAME = "com.codi.mvptest.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "user_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context mContext;
    private final File mCacheDir;
    private final JsonSerializer mSerializer;
    private final FileManager mFileManager;
    private final ThreadExecutor mThreadExecutor;

    private UserCacheImpl(Context context, JsonSerializer jsonSerializer, FileManager fileManager, ThreadExecutor executor) {
        if(context == null || jsonSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.mContext = context.getApplicationContext();
        this.mCacheDir = this.mContext.getCacheDir();
        this.mSerializer = jsonSerializer;
        this.mFileManager = fileManager;
        this.mThreadExecutor = executor;
    }

    @Override
    public synchronized void get(int userId, UserCacheCallback callback) {
        File userEntityFile = this.buildFile(userId);
        String userString = mFileManager.readFileContent(userEntityFile);
        UserEntity userEntity = mSerializer.deserialize(userString);
        if (userEntity != null) {
            callback.onUserEntityLoaded(userEntity);
        } else {
            callback.onError(new UserNotFoundException());
        }
    }

    @Override
    public synchronized void put(UserEntity userEntity) {
        if (userEntity != null) {
            if(!isCached(userEntity.getUserId())) {
                File buildFile = buildFile(userEntity.getUserId());
                String fileContent = mSerializer.seialize(userEntity);
                executeAsynchronously(new CacheWriter(mFileManager, buildFile, fileContent));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int userId) {
        File file = buildFile(userId);
        return mFileManager.exists(file);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long updatedTime = getLastCacheUpdateTimeMillis();
        boolean expired = ((currentTime - updatedTime) > EXPIRATION_TIME);
        if(expired) {
            evictAll();
        }
        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(mFileManager, mCacheDir));
    }

    private void executeAsynchronously(Runnable runnable) {
        this.mThreadExecutor.execute(runnable);
    }

    private static class CacheWriter implements Runnable {

        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    private static class CacheEvictor implements Runnable {

        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(cacheDir);
        }
    }

    private File buildFile(int userId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.mCacheDir.getPath()).append(File.separator).append(DEFAULT_FILE_NAME).append(userId);
        return new File(fileNameBuilder.toString());
    }

    private void setLastCacheUpdateTimeMillis() {
        this.mFileManager.writeToPreferences(mContext, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE, System.currentTimeMillis());
    }

    private long getLastCacheUpdateTimeMillis() {
        return this.mFileManager.getFromPreferences(this.mContext, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE);
    }
}
