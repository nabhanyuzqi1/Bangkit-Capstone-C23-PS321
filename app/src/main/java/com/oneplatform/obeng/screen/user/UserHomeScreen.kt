@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
)

package com.oneplatform.obeng.screen.user

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.size.Scale
import com.oneplatform.obeng.R
import com.oneplatform.obeng.api.TechnicianApiService
import com.oneplatform.obeng.model.NavDrawerItem
import com.oneplatform.obeng.model.TechnicianViewModel
import com.oneplatform.obeng.model.navDrawerItems
import com.oneplatform.obeng.screen.components.CustomUserProfileCard
import com.oneplatform.obeng.screen.components.HomeScreenHeader
import com.oneplatform.obeng.screen.components.UserHomeNavbar
import com.oneplatform.obeng.ui.theme.White10
import com.oneplatform.obeng.ui.theme.orange
import com.oneplatform.obeng.ui.theme.primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserHomeScreen(navController: NavController,
                   authStateManager: AuthStateManager,
                   apiService: TechnicianApiService,
                   context: Context,
                   ) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val selectedItem = remember { mutableStateOf<NavDrawerItem?>(null) } // Nullable selectedItem
    val backClickCount = remember { mutableStateOf(0) }
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher


    val technicianViewModel = remember { TechnicianViewModel(apiService) }
    LaunchedEffect(Unit) {
        technicianViewModel.fetchTechnicians()
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
        }
    ) {
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
                        HomeScreenHeader(drawerState = drawerState)
                    }
                }
            },
            bottomBar = {
                UserHomeNavbar(navController = navController)
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
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(top = 60.dp, bottom = 60.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),

                            ) {
                            if (technicianViewModel.technicians.isEmpty()) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator() // Show a loading indicator
                                }
                            } else {
                                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(10.dp)) {
                                    items(technicianViewModel.technicians) { technician ->


                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                                .padding(6.dp)
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(White10)
                                                .clickable {
                                                    technicianViewModel.selectedTechnician = technician
                                                    navController.navigate("tech_information/${technician.technicianId}")
                                                }
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp)
                                            ) {
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Box(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Image(
                                                        painter = rememberImagePainter(
                                                            data = technician.fotoProfil,
                                                            builder = {
                                                                scale(Scale.FILL) // Scale the image to fill the available space
                                                                placeholder(R.drawable.ic_camera_placeholder) // Show a placeholder while loading the image
                                                                error(R.drawable.ic_technician) // Show an error image if the loading fails
                                                                crossfade(true) // Apply a crossfade animation when loading the image
                                                                allowHardware(false) // Disable hardware bitmap mode to conserve memory
                                                                memoryCachePolicy(CachePolicy.ENABLED) // Enable memory caching
                                                                diskCachePolicy(CachePolicy.ENABLED) // Enable disk caching
                                                                networkCachePolicy(CachePolicy.ENABLED) // Enable network caching
                                                            }
                                                        ),
                                                        contentDescription = "Profile Image",
                                                        modifier = Modifier
                                                            .size(100.dp)
                                                            .clip(RoundedCornerShape(5.dp))
                                                    )

                                                }
                                                Spacer(modifier = Modifier.height(16.dp))
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    Column(
                                                        modifier = Modifier
                                                            .wrapContentHeight()
                                                    ) {
                                                        technician.nama?.let { it1 ->
                                                            Text(
                                                                text = it1,
                                                                fontSize = 14.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color.Black,
                                                            )
                                                        }
                                                        technician.keahlian?.let { it1 ->
                                                            Text(
                                                                text = it1,
                                                                fontSize = 12.sp,
                                                                color = primary,
                                                            )
                                                        }
                                                    }
                                                    Box(
                                                        modifier = Modifier
                                                            .clip(RoundedCornerShape(8.dp))
                                                            .background(Color.White)
                                                            .align(Alignment.Top)
                                                    ) {
                                                        Row(
                                                            horizontalArrangement = Arrangement.End,
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Icon(
                                                                modifier = Modifier.size(16.dp, 16.dp),
                                                                imageVector = Icons.Default.Star,
                                                                contentDescription = "Rating Icon",
                                                                tint = orange
                                                            )
                                                            Text(
                                                                text = technician.uniqueNumericId.toString(),
                                                                fontSize = 14.sp,
                                                                fontWeight = FontWeight.Medium,
                                                                color = Color.Black,
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }


                        }
                    }
                }
            }
        )
    }
}





data class Technician(
    val name: String,
    val expertise: String,
    val rating: Float
)


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val navController = rememberNavController()
    val apiService = TechnicianApiService.create() // Instantiate the apiService
    UserHomeScreen(
        navController = navController,
        authStateManager = AuthStateManager(context = LocalContext.current),
        apiService = apiService,
        context = LocalContext.current
    )
}
