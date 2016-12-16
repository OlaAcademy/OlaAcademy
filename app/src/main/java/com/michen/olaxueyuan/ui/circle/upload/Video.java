package com.michen.olaxueyuan.ui.circle.upload;

import android.graphics.Bitmap;

public class Video {
    private int id;
    private String displayName;
    private String mimeType;
    private String path;
    private long size;
    private long duration;
    private long date;
    private Bitmap thumbnailBitmap;

    /**
     *
     */
    public Video() {
        super();
    }

    /**
     * @param id
     * @param displayName
     * @param mimeType
     * @param size
     * @param duration
     * @param date
     */
    public Video(int id, String displayName, String mimeType, String path, long size, long duration, long date) {
        super();
        this.id = id;
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
        this.duration = duration;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Bitmap getThumbnailBitmap() {
        return thumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
        this.thumbnailBitmap = thumbnailBitmap;
    }
}