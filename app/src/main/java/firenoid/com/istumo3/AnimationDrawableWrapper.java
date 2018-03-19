package firenoid.com.istumo3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.VectorDrawable;

import java.sql.Wrapper;

/**
 * Created by OpiFrame on 19/12/2017.
 */

public class AnimationDrawableWrapper extends DrawableWrapper {

    private float rotation;
    private Rect bounds;

    public AnimationDrawableWrapper(Resources resources, Drawable drawable) {
        super(vectorToBitmapDrawableIfNeeded(resources, drawable));
        bounds = new Rect();
    }

    @Override
    public void draw(Canvas canvas) {
        copyBounds(bounds);
        canvas.save();
        canvas.rotate(rotation, bounds.centerX(), bounds.centerY());
        super.draw(canvas);
        canvas.restore();
    }

    public void setRotation(float degrees) {
        this.rotation = degrees % 360;
        invalidateSelf();
    }

    /**
     * Workaround for issues related to vector drawables rotation and scaling:
     * https://code.google.com/p/android/issues/detail?id=192413
     * https://code.google.com/p/android/issues/detail?id=208453
     */
    private static Drawable vectorToBitmapDrawableIfNeeded(Resources resources, Drawable drawable) {
        if (drawable instanceof VectorDrawable) {
            Bitmap b = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
            drawable.draw(c);
            drawable = new BitmapDrawable(resources, b);
        }
        return drawable;
    }
}