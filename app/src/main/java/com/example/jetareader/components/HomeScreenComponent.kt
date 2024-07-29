package com.example.jetareader.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetareader.navigation.ReaderAppScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

//@Preview(showBackground = true, device = Devices.PIXEL_6_PRO, showSystemUi = true )
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopAppBar(
    userName: String = "Sujan",
    showProfile : Boolean = true,
    navController: NavController = rememberNavController(),
    onSignOutClick: () -> Unit ={}
) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showSignOutMessage by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // User Icon
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(15))
                        .clickable { }
                )

                Spacer(modifier = Modifier.width(8.dp))

                // User Name
                Text(text = userName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.weight(1f))

                // Sign Out Icon
                IconButton(onClick = {
                    onSignOutClick.invoke()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Sign Out",
                        tint = Color.Black
                    )
                }
            }
        },
        actions = {},
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onPrimary)
    )


}

@Preview(showBackground = true)
@Composable
fun FabContent(
    onTap: (String) -> Unit ={}
) {
    FloatingActionButton(
        onClick = { onTap("")  },
        containerColor = Color(0xFF06497E),
        shape = RoundedCornerShape(50)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = Color.White
        )
    }
}

