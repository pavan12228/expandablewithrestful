package justjava.android.example.packagecom.Expandabale.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import justjava.android.example.packagecom.Expandabale.R;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by Dell on 8/30/2016.
 */
public class Util {
    Context context;
    boolean mAccess_fine_location, mReadContacts;
    private static final String MyPREFERENCES = "MyPrefs";
    private static SharedPreferences sharedpreferences;
    MaterialDialog ringProgressDialog = null;


    public Util(Context context) {
        this.context = context;
    }

    public boolean getStatusPermission() {
        return mAccess_fine_location;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mAccess_fine_location = true;
        } else {
            mAccess_fine_location = false;
        }

        if (grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            mReadContacts = true;
        } else {
            mReadContacts = false;
        }


    }


    public void checkExplainRequestPermission(final String mPermission, final String accessCoarseLocation) {
        if (ContextCompat.checkSelfPermission(context, mPermission) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,accessCoarseLocation)==PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission granted!", Toast.LENGTH_SHORT).show();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, mPermission)&& ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, accessCoarseLocation)) {
            AlertDialog.Builder explantDilaog = new AlertDialog.Builder(context);
            explantDilaog.setMessage("Allow wateronwheels to access this device Location");
            explantDilaog.setTitle("Please click ok to continue");
            explantDilaog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{mPermission,accessCoarseLocation}, 151);
                    Toast.makeText(context, "App requires GPS Permmision for your location", Toast.LENGTH_SHORT).show();
                }
            });
            explantDilaog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            explantDilaog.show();


        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 151);
            Toast.makeText(context, "No permission", Toast.LENGTH_SHORT).show();
        }
    }


    public static SharedPreferences getSharedpreferences(Context context) {
        if (sharedpreferences == null)
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences;
    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(String.valueOf(key), String.valueOf(value));
        editor.commit();
    }

    public static String getString(String key) {
        return sharedpreferences.getString(key, "");
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key) {
        return sharedpreferences.getBoolean(key, false);
    }

    public static void setInt(String key, int value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, Integer.parseInt(String.valueOf(value)));
        editor.commit();
    }

    public static int getInt(String key) {
        return sharedpreferences.getInt(key, 0);
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    public  void showProgressDialog(final Context ctx) {
        if (ringProgressDialog == null) {
            ringProgressDialog = new MaterialDialog.Builder(ctx)
                    .title(ctx.getResources().getString(R.string.app_name))
                    .progress(true, 0)
                    .theme(Theme.LIGHT)
                    .cancelable(false)
                    .show();
            ringProgressDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    ProgressBar v = (ProgressBar) ringProgressDialog.findViewById(android.R.id.progress);
                    v.getIndeterminateDrawable().setColorFilter(ctx.getResources().getColor(R.color.colorPrimary),
                            android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            });
        }
    }

    public void dismissDialog() {
        if (ringProgressDialog != null) {
            if (ringProgressDialog.isShowing()) {
                ringProgressDialog.dismiss();
                ringProgressDialog = null;
            }
        }
    }

    public void singelButtonAlertDialog(Context ctx, String title, String message) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
        builder.theme(Theme.LIGHT);
        if (title != null) {
            builder.title(title);
        }
        builder.content(message);

        builder.positiveText("Ok");

        builder.positiveColorRes(R.color.colorPrimary);
        builder.cancelable(false);
        builder.show();

    }

    public void singelButtonAlertDialog(Context ctx, String title, String message, final MaterialDialog.ButtonCallback cb) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
        builder.theme(Theme.LIGHT);
        if (title != null) {
            builder.title(title);
        }
        builder.content(message);

        builder.positiveText("Yes");
        builder.negativeText("No");

        builder.positiveColorRes(R.color.colorPrimary);
        builder.negativeColorRes(R.color.md_edittext_error);
        builder.callback(cb);
        builder.cancelable(false);
        builder.show();

    }


    public void twoButtonAlertDialog(Context ctx, String title, String message, boolean okCancel,
                                     final MaterialDialog.ButtonCallback cb) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
        builder.theme(Theme.LIGHT);
        if (title != null) {
            builder.title(title);
        }
        builder.content(message);

        if (okCancel) {
            builder.negativeText(android.R.string.cancel);
            builder.positiveText(android.R.string.ok);
        } else {
            builder.negativeText("No");
            builder.positiveText("Yes");
        }

        builder.positiveColorRes(R.color.colorPrimary);
        builder.negativeColorRes(R.color.md_edittext_error);
        builder.callback(cb);
        builder.cancelable(false);
        builder.show();

    }


    public ServiceOperations getBaseClassService(Context ctx, String url) {
        return new RetroHelper().getAdapter(ctx, url).create(ServiceOperations.class);
    }







    public void serviceCallFailermsg(RetrofitError error, Context mcontext) {
        if (error != null) {
            try {
                String body = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                Log.v("failure", body.toString());
                JsonObject errorJson = new Gson().fromJson(body, JsonObject.class);
                singelButtonAlertDialog(mcontext, mcontext.getResources().getString(R.string.app_name), errorJson.get(StringConstants.message).getAsString());
            } catch (Exception e) {
                singelButtonAlertDialog(mcontext, mcontext.getResources().getString(R.string.app_name), mcontext.getString(R.string.app_name));
            }
        } else {
            singelButtonAlertDialog(mcontext, mcontext.getResources().getString(R.string.app_name), mcontext.getString(R.string.app_name));
        }
    }

    public void showToast(Context c,String msg) {
        Toast.makeText(c, "" + msg.toString(), Toast.LENGTH_SHORT).show();
    }

}

