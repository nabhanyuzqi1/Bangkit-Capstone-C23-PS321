@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.oneplatform.obeng.screen.technician

import AuthStateManager
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oneplatform.obeng.R
import com.oneplatform.obeng.api.apiService
import com.oneplatform.obeng.model.NavDrawerItem
import com.oneplatform.obeng.model.TechnicianViewModel
import com.oneplatform.obeng.model.navDrawerItems
import com.oneplatform.obeng.screen.components.CustomTopBarTitleBack
import com.oneplatform.obeng.screen.components.CustomUserProfileCard
import com.oneplatform.obeng.screen.components.TechHomeNavbar
import com.oneplatform.obeng.ui.theme.White10
import com.oneplatform.obeng.ui.theme.primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TechHomeScreen(navController: NavController, authStateManager: AuthStateManager, context: Context) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val selectedItem = remember { mutableStateOf<NavDrawerItem?>(null) } // Nullable selectedItem
    val backClickCount = remember { mutableStateOf(0) }
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    var technicians by remember {
        mutableStateOf<List<TechnicianViewModel.Technician>>(emptyList())
    }

    DisposableEffect(backPressedDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (authStateManager.getAuthState()) {
                    if (backClickCount.value < 1) {
                        // Show a toast message on the first click
                        Toast.makeText(context, "Click again to close", Toast.LENGTH_SHORT).show()
                        backClickCount.value++
                        scope.launch {
                            // Reset the click count after a certain delay (e.g., 2 seconds)
                            delay(2000)
                            backClickCount.value = 0
                        }
                    } else {
                        // Close the app after double back button click
                        isEnabled = false // Disable the callback to avoid triggering it multiple times
                        backPressedDispatcher.onBackPressed()

                        // Emit a system back button event to close the app
                    }
                } else {
                    // If the user is not logged in, allow the back button to work as usual
                    navController.popBackStack()
                }
            }
        }
        backPressedDispatcher.addCallback(callback)

        // Cleanup the callback when the composable is removed from the composition
        onDispose {
            callback.remove()
        }
    }

    LaunchedEffect(Unit) {
        selectedItem.value = null
        backClickCount.value = 0

        try {
            val fetchedTechnicians = apiService.getTechnicians()
            technicians = fetchedTechnicians
        } catch (e: Exception) {
            // Handle the exception or show an error message
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                CustomUserProfileCard()
                Spacer(Modifier.height(12.dp))
                navDrawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    ConstraintLayout {
                        val (topappbarbgref) = createRefs()
                        Box(
                            modifier = Modifier
                                .constrainAs(topappbarbgref) {
                                    top.linkTo(topappbarbgref.top)
                                    bottom.linkTo(topappbarbgref.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            CustomTopBarTitleBack(navController = navController, title = "Welcome, ", withBack = false)

                        }
                    }
                },
                bottomBar = {
                    TechHomeNavbar(navController = navController)
                },
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(top = 70.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp)
                            ) {
                                technicians.forEach { technician ->
                                    // Display technician information in your UI elements
                                    // For example, technician.nama, technician.keahlian, etc.
                                    JobSection(navController)
                                    Spacer(modifier = Modifier.padding(10.dp))
                                }
                            }
                        }
                    }
                }
            )
        }
    )

}

@Composable
fun JobSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(White10)
                .clickable {
                    navController.navigate("tech_information")
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp),
                        painter = painterResource(R.drawable.ic_technician),
                        contentDescription = "",
                    )
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Thommy",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        Text(
                            text = "Jakarta, Indonesia",
                            fontSize = 12.sp,
                            color = primary,
                        )
                        Spacer(modifier = Modifier.padding(top=10.dp))
                        Text(
                            text = "Ban gue meledak gan tolonggkkk",
                            fontSize = 12.sp,
                            color = primary,
                        )
                    }

                }
            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun TechHomeScreenPreview(){
    val navController = rememberNavController()
    TechHomeScreen(navController = navController, authStateManager = AuthStateManager(context = LocalContext.current), context = LocalContext.current)
}