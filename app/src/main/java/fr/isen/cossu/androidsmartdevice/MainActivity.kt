
package fr.isen.cossu.androidsmartdevice

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fr.isen.cossu.androidsmartdevice.ui.theme.AndroidSmartDeviceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSmartDeviceTheme {
                UpdatedPresentationScreen(onStartClicked = {
                    checkPermissionsAndStart()
                })
            }
        }
    }

    private fun checkPermissionsAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionsNeeded = mutableListOf<String>()
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.BLUETOOTH_SCAN)
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (permissionsNeeded.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionsNeeded.toTypedArray(),
                    1
                )
            } else {
                startListeActiviter()
            }
        } else {
            startListeActiviter()
        }
    }

    private fun startListeActiviter() {
        val intent = Intent(this, ListeActiviter::class.java)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startListeActiviter()
            } else {
                Toast.makeText(this, "Les autorisations Bluetooth sont nécessaires pour scanner les appareils.", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun UpdatedPresentationScreen(onStartClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_camera), // Exemple d'icône par défaut
                contentDescription = "Logo de l'application",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bienvenue sur Android Smart Device",
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = "Votre solution simple et rapide pour détecter et connecter vos appareils Bluetooth Low Energy. Lancez des recherches, connectez vos équipements, et gérez vos appareils intelligents avec facilité.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 8.dp),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onStartClicked,
            modifier = Modifier.fillMaxWidth(0.6f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Commencer l'exploration", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Facilitez vos connexions Bluetooth dès aujourd'hui.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}
