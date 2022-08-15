package com.daily.app.common.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*


class LocationUtils {

    companion object {
        fun getCurrentLocation(context: Context) : Location? {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            var locationByGps: Location? = null
            var locationByNetwork: Location? = null

            val gpsLocationListener: LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    locationByGps = location
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            val networkLocationListener: LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    locationByNetwork = location
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            if (hasGps && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    gpsLocationListener
                )
            }

            if (hasNetwork) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    0F,
                    networkLocationListener
                )
            }

            val lastKnownLocationByGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastKnownLocationByGps?.let {
                locationByGps = lastKnownLocationByGps
            }

            val lastKnownLocationByNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            lastKnownLocationByNetwork?.let {
                locationByNetwork = lastKnownLocationByNetwork
            }

            var currentLocation: Location? = null

            if (locationByGps != null && locationByNetwork != null) {
                currentLocation = if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {
                    locationByGps!!
                } else {
                    locationByNetwork!!
                }
            } else if (locationByGps != null) {
                currentLocation = locationByGps
            } else {
                currentLocation = locationByNetwork
            }

            return currentLocation
        }


        fun getAddress(context: Context, lat: Double, lng: Double) : Address? {
            val geocoder = Geocoder(context, Locale.getDefault())
            var address: Address? = null
            try {
                val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
                address = addresses[0]
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return address
        }

        fun getDefaultCity(code: String) : String? {
            val cities = hashMapOf(
                "AF" to "Kabul",
                "AL" to "Tirana",
                "DZ" to "Algiers",
                "AD" to "Andorra la Vella",
                "AO" to "Luanda",
                "AG" to "St. John's",
                "AR" to "Buenos Aires",
                "AM" to "Yerevan",
                "AU" to "Canberra",
                "AT" to "Vienna",
                "AZ" to "Baku",
                "BS" to "Nassau",
                "BH" to "Manama",
                "BD" to "Dhaka",
                "BB" to "Bridgetown",
                "BY" to "Minsk",
                "BE" to "Brussels",
                "BZ" to "Belmopan",
                "BJ" to "Porto Novo",
                "BT" to "Thimphu",
                "BO" to "Sucre",
                "BA" to "Sarajevo",
                "BW" to "Gaborone",
                "BR" to "Brasilia",
                "BN" to "Bandar Seri Begawan",
                "BG" to "Sofia",
                "BF" to "Ouagadougou",
                "BI" to "Bujumbura",
                "KH" to "Phnom Penh",
                "CM" to "Yaoundé",
                "CA" to "Ottawa",
                "CV" to "Praia",
                "CF" to "Bangui",
                "TD" to "N'Djamena",
                "CL" to "Santiago",
                "CN" to "Beijing",
                "CO" to "Bogota",
                "KM" to "Moroni",
                "CR" to "San José",
                "HR" to "Zagreb",
                "CU" to "Havana",
                "CY" to "Nicosia",
                "CZ" to "Prague",
                "CD" to "Kinshasa",
                "DK" to "Copenagen",
                "DJ" to "Djibouti",
                "DM" to "Roseau",
                "DO" to "Santo Domingo",
                "TL" to "Dili",
                "EC" to "Quito",
                "EG" to "Cairo",
                "SV" to "San Salvador",
                "GQ" to "Malabo",
                "ER" to "Asmara",
                "EE" to "Tallinn",
                "SZ" to "Mbabane",
                "ET" to "Addis Ababa",
                "FJ" to "Suva",
                "FI" to "Helsinki",
                "FR" to "Paris",
                "GA" to "Libreville",
                "GM" to "Banjul",
                "GE" to "Tbilisi",
                "DE" to "Berlin",
                "GH" to "Accra",
                "GR" to "Athens",
                "GD" to "St. George's",
                "GT" to "Guatemala City",
                "GN" to "Conakry",
                "GW" to "Bissau",
                "GY" to "Georgetown",
                "HT" to "Port-au-Prince",
                "HN" to "Tegucigalpa",
                "HU" to "Budapest",
                "IS" to "Reykjavík",
                "IN" to "New Delhi",
                "ID" to "Jakarta",
                "IR" to "Tehran",
                "IQ" to "Baghdad",
                "IE" to "Dublin",
                "IL" to "Jerusalem",
                "IT" to "Rome",
                "CI" to "Yamoussoukro",
                "JM" to "Kingston",
                "JP" to "Tokyo",
                "JO" to "Amman",
                "KZ" to "Astana",
                "KE" to "Nairobi",
                "KI" to "Tarawa",
                "KP" to "Pyongyang",
                "KR" to "Seoul",
                "-" to "Pristina",
                "KW" to "Kuwait City",
                "KG" to "Bishkek",
                "LA" to "Vientiane",
                "LV" to "Riga",
                "LB" to "Beirut",
                "LS" to "Maseru",
                "LR" to "Monrovia",
                "LY" to "Tripoli",
                "LI" to "Vaduz",
                "LT" to "Vilnius",
                "LU" to "Luxembourg",
                "MG" to "Antananarivo",
                "MW" to "Lilongwe",
                "MY" to "Kuala Lumpur",
                "MV" to "Malé",
                "ML" to "Bamako",
                "MT" to "Valletta",
                "MH" to "Majuro",
                "MR" to "Nouakchott",
                "MU" to "Port Louis",
                "MX" to "Mexico City",
                "FM" to "Palikir",
                "MD" to "Chisinau",
                "MC" to "Monaco",
                "MN" to "Ulaanbaatar",
                "ME" to "Podgorica",
                "MA" to "Rabat",
                "MZ" to "Maputo",
                "MM" to "Naypyidaw",
                "NA" to "Windhoek",
                "NR" to "Yaren",
                "NP" to "Kathmandu",
                "NL" to "Amsterdam",
                "NZ" to "Wellington",
                "NI" to "Managua",
                "NE" to "Niamey",
                "NG" to "Abuja",
                "MK" to "Skopje",
                "NO" to "Oslo",
                "OM" to "Muscat",
                "PK" to "Islamabad",
                "PW" to "Melekeok",
                "PS" to "East Jerusalem",
                "PA" to "Panama City",
                "PG" to "Port Moresby",
                "PY" to "Asunción",
                "PE" to "Lima",
                "PH" to "Manila",
                "PL" to "Warsaw",
                "PT" to "Lisbon",
                "QA" to "Doha",
                "CG" to "Brazzaville",
                "RO" to "Bucharest",
                "RU" to "Moscow",
                "RW" to "Kigali",
                "KN" to "Basseterre",
                "LC" to "Castries",
                "VC" to "Kingstown",
                "WS" to "Apia",
                "SM" to "San Marino",
                "ST" to "São Tomé",
                "SA" to "Riyadh",
                "SN" to "Dakar",
                "RS" to "Belgrade",
                "SC" to "Victoria",
                "SL" to "Freetown",
                "SG" to "Singapore",
                "SK" to "Bratislava",
                "SI" to "Ljubljana",
                "SB" to "Honiara",
                "SO" to "Mogadishu",
                "ZA" to "Pretoria",
                "SS" to "Juba",
                "ES" to "Madrid",
                "LK" to "Sri Jayawardenapura Kotte",
                "SD" to "Khartoum",
                "SR" to "Paramaribo",
                "SE" to "Stockholm",
                "CH" to "Bern",
                "SY" to "Damascus",
                "TW" to "Taipei",
                "TJ" to "Dushanbe",
                "TZ" to "Dodoma",
                "TH" to "Bangkok",
                "TG" to "Lomé",
                "TO" to "Nukualofa",
                "TT" to "Port of Spain",
                "TN" to "Tunis",
                "TR" to "Ankara",
                "TM" to "Ashgabat",
                "TV" to "Funafuti",
                "UG" to "Kampala",
                "UA" to "Kiev",
                "AE" to "Abu Dhabi",
                "GB" to "London",
                "US" to "Washington D.C.",
                "UY" to "Montevideo",
                "UZ" to "Tashkent",
                "VU" to "Port Vila",
                "VA" to "Vatican City",
                "VE" to "Caracas",
                "VN" to "Hanoi",
                "YE" to "Sana'a",
                "ZM" to "Lusaka",
                "ZW" to "Harare"
                )

            return cities[code]
        }
    }
}