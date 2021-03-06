/*
 * Copyright (C) 2014 The OmniRom Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.systemui.batterysaver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.android.systemui.R;

public class AirPlaneChangeMode extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean enabled = Settings.Global.getInt(context.getContentResolver(),
                      Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

        // Change the system setting
        Settings.Global.putInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON,
                                enabled ? 1 : 0);

        // Post the intent
        Intent newIntent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        newIntent.putExtra("state", enabled);
        context.sendBroadcast(newIntent);

        // Dismiss the notification that brought us here.
        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(BatterySaverService.BATTERY_SAVER_NOTIFICATION_ID);
    }

}
