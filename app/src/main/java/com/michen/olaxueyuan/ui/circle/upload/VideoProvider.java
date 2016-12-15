package com.michen.olaxueyuan.ui.circle.upload;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class VideoProvider implements AbstractProvider {
    private Context context;

    public VideoProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<?> getList() {
        List<Video> list = null;
        String selection = MediaStore.Video.Media.MIME_TYPE + " like ? and " + MediaStore.Video.Media.SIZE + " < ? ";
        String selectionArgs[] = new String[]{"video%", "10485760"};
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, selection, selectionArgs, null);
            if (cursor != null) {
                list = new ArrayList<Video>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                    String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                    long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED));
//                    Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
                    Video video = new Video(id, displayName, mimeType, path, size, duration, date);
                    list.add(video);
                }
                cursor.close();
            }
        }
        return list;
    }

}