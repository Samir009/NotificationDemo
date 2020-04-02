package com.samir.luniva.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonSyntaxException;
import com.samir.luniva.R;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.HttpException;

public class UtilitiesFunctions {

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }


    public static String handleApiError(Throwable error) {

        if (error instanceof HttpException) {

            switch (((HttpException) error).code()) {

                case HttpsURLConnection.HTTP_NOT_FOUND:
                    return "Error code 404 not found,  Please try again later.";

                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    return "Please check your username & password! Please try again later.";

                case HttpsURLConnection.HTTP_FORBIDDEN:
                    return "Forbidden error . Logout & Re-login if you are facing some error.";

                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    return "Internal Server Error. Please try again later we will fix this soon.";

                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    return "Sorry! Bad Request. Please try again later.";
                case 0:
                    return "No Internet Connection.  Please try again later.";
                default:
                    return error.getLocalizedMessage();

            }
        } else if (error instanceof JsonSyntaxException) {
            return "Something went wrong. PLease contact our support!";
        } else {
            return error.getMessage();
        }
    }


    public static void callDialer(Context context, String phone) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                "tel", phone, null));
        context.startActivity(phoneIntent);
    }

    public static void messageContent(Context context, String phone) {
        Intent smsMsgAppVar = new Intent(Intent.ACTION_VIEW);
        smsMsgAppVar.setData(Uri.parse("sms:" + phone));
        smsMsgAppVar.putExtra("sms_body", "your ");
        context.startActivity(smsMsgAppVar);
    }


    public static void directionApiContent(Context context, String unSplitLatLng, String label) {

        if (unSplitLatLng != null) {
            String[] splitLatLng = unSplitLatLng.split(",");
            if (splitLatLng.length > 1) {
                final ArrayList<String> splittingLatLng = new ArrayList<>();
                splittingLatLng.addAll(Arrays.asList(splitLatLng));
                String latitude = splittingLatLng.get(0);
                String longitude = splittingLatLng.get(1);

                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    try {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(unrestrictedIntent);
                    } catch (ActivityNotFoundException innerEx) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.maps.google.com/?q=@" + latitude + "," + longitude));
                        context.startActivity(browserIntent);
                    }
                }

            } else {
                Toast.makeText(context, "Location is not available", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(context, "Location is not available", Toast.LENGTH_SHORT).show();
        }
    }

    //DATE PARSER

    public static String simpleformatToSeverDateOnly(String unformattedDate) {
        String currentTimeZoneDate = "";
        try {
            DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
            Date date = sdf.parse(unformattedDate);
            sdf.setTimeZone(TimeZone.getDefault());
            currentTimeZoneDate = sdf.format(date);
            date = sdf.parse(currentTimeZoneDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            currentTimeZoneDate = simpleDateFormat.format(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return currentTimeZoneDate;
    }

    public static String simpleFormatToClientDateOnly(String unformattedDate) {
        String currentTimeZoneDate = "";
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
            Date date = sdf.parse(unformattedDate);
            sdf.setTimeZone(TimeZone.getDefault());
            currentTimeZoneDate = sdf.format(date);
            date = sdf.parse(currentTimeZoneDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
            currentTimeZoneDate = simpleDateFormat.format(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return currentTimeZoneDate;
    }

    public static String formatToClientFullDateTime(String unformattedDate) {
        String currentTimeZoneDate = "";
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:45"));
            Date date = sdf.parse(unformattedDate);
            currentTimeZoneDate = sdf.format(date);
            date = sdf.parse(currentTimeZoneDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.ENGLISH);
            currentTimeZoneDate = simpleDateFormat.format(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return currentTimeZoneDate;
    }

    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static void showSnackBar(final Context context, View view, String message) {

        final Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_SHORT);

        snackbar.setActionTextColor(Color.WHITE);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        snackbar.setDuration(10000);
        snackbar.show();
    }

    public static void shareAppIntent(Context context, String title) {

        String appName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                title + ":- Hey check out this app at: https://play.google.com/store/apps/details?id=" + appName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);

    }

    public static void emailcontent(Context context, String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nagar App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, " Please mention your contact details below. Thank you");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    private static void setClipboard(Context context, String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
    }


    public static String durationEnglish(String unformatDate) {
        String currentTime = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            /**
             * convert date from UTC to GMT format
             */
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = dateFormat.parse(unformatDate);
            dateFormat.setTimeZone(TimeZone.getDefault());
            Date serverDate = dateFormat.parse(dateFormat.format(date));
            Date currentDate = new Date();
            long diff = currentDate.getTime() - serverDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            if (serverDate.before(currentDate)) {
                if (!String.valueOf(days).equals("0")) {
                    currentTime = days + " days ago";
                    if (days > 30) {
                        currentTime = String.valueOf(days / 30) + " month ago";
                    }
                } else if (!String.valueOf(hours).equals("0")) {
                    currentTime = hours + " hour ago";
                } else if (!String.valueOf(minutes).equals("0")) {
                    currentTime = minutes + " minute ago";
                } else {
                    currentTime = "Just now";
                }
            }
        } catch (ParseException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return currentTime;
    }

}
