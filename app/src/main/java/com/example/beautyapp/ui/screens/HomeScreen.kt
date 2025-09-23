package com.example.beautyapp.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.beautyapp.R
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple


@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // Track background color dynamically based on scroll position
    val backgroundColor by remember {
        derivedStateOf {
            if (scrollState.value > 100) Color.White else Color.Transparent
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Ensures full screen is scrollable
            .padding(bottom = 56.dp) // Space for bottom navigation bar
    ) {
        Box(modifier = Modifier.fillMaxSize())
        {
            Image(
                painter = rememberAsyncImagePainter(model = "https://yourimageurl.com/banner.png"),
                contentDescription = "Promo Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(385.dp)
            )
            Column(
            ) {
                // 🔹 Location & Buy Elite Section
                Row(
//                    modifier = Modifier
//                        .clickable { navController.navigate("location") },
                      modifier = Modifier.clickable(
                          indication = rememberRipple(), // 🔹 Ripple effect on click
                          interactionSource = remember { MutableInteractionSource() } // 🔹 Tracks touch events
                      ) {
                        navController.navigate("location")
                      }
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) { // Keeps text and icon in one line
                            Text(
                                text = "Dhanori",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Arrow",
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(start = 4.dp) // Adds space between text and icon
                            )
                        }
                        Text(
                            "1, Dhanori-Lohegaon Rd, Parande...",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Button(
                        onClick = { /* Handle Buy Elite */ },
                        colors = ButtonDefaults.buttonColors(Color(0xFFD4AF37)), // Gold Color
                        shape = CircleShape
                    ) {
                        Text("Buy Elite", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            // 🔹 Search Bar
                AnimatedSearchBar(navController)
            }
        }

        // 🔹 Loot of the Year Row
        MarqueeText()

        OffersSection()

        // 🔹 Everything That You Need - Services Section
        Text(
            text = "Everything That You Need",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            ServiceItem(R.drawable.ic_launcher_foreground, "Salon At Home")
            ServiceItem(R.drawable.ic_launcher_foreground, "Pre Bridal")
            ServiceItem(R.drawable.ic_launcher_foreground, "Advance")
            //ServiceItem(R.drawable.ic_launcher_background, "") // Placeholder for last item
        }
        Spacer(modifier = Modifier.height(15.dp))
        EliteBanner()
        Spacer(modifier = Modifier.height(28.dp))
        TrendingServicesSection()
        Spacer(modifier = Modifier.height(28.dp))
        ChatWithCosmetologist()
        Spacer(modifier = Modifier.height(28.dp))
        HydraTreatmentsUI()
        Spacer(modifier = Modifier.height(28.dp))
        //Trending near you section
        HydraTreatmentsUI_Updated()
        Spacer(modifier = Modifier.height(28.dp))
        HorizontalScrollCardWithDots()
        Spacer(modifier = Modifier.height(28.dp))
        SalonPackageList()
        Spacer(modifier = Modifier.height(28.dp))
        ChatWithCosmetologist_Updated()
        Spacer(modifier = Modifier.height(28.dp))
        SalonHomeScreen()
        Spacer(modifier = Modifier.height(28.dp))
        ChatWithCosmetologist_UpdatedNew()
        Spacer(modifier = Modifier.height(28.dp))
        SalonHomeScreen_Updated()
        Spacer(modifier = Modifier.height(28.dp))
        VideoListScreen_Women(navController)
        Spacer(modifier = Modifier.height(40.dp))
    }
    CustomBottomNavigationBar(navController)
}

@Composable
fun OffersSection() {
    val offers = listOf(
        OfferData(
            badgeTimeInMillis = System.currentTimeMillis() + (15 * 60 * 60 * 1000) + (37 * 60 * 1000), // 15h 37m from now
            title = "Korean Clean-Up",
            price = "₹0"
        ),
        OfferData(
            badgeTimeInMillis = null, // No countdown for this card
            title = "Packages with Free Korean Clean-Up",
            price = "₹1499"
        )
    )

    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(offers) { offer ->
            OfferCard(offer)
        }
    }
}

// ✅ Data Class for Offer Details
data class OfferData(val badgeTimeInMillis: Long?, val title: String, val price: String)

// ✅ Offer Card with **Real-Time Countdown Timer**
@Composable
fun OfferCard(offer: OfferData) {
    val countdownTime = remember { mutableStateOf(calculateRemainingTime(offer.badgeTimeInMillis)) }

    // 🔹 Continuously Update Timer
    LaunchedEffect(offer.badgeTimeInMillis) {
        while (offer.badgeTimeInMillis != null && countdownTime.value != "Started") {
            delay(1000) // Updates every second
            countdownTime.value = calculateRemainingTime(offer.badgeTimeInMillis)
        }
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(188.dp, 120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color(0xFFFFE0F0)), // Light Pink Background
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // 🔹 Countdown Timer Badge (Absolutely Positioned at Top-Left)
            if (offer.badgeTimeInMillis != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart) // Position it at the top-left corner
                        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Starts In: ${countdownTime.value}",
                        fontSize = 11.sp, // Slightly larger
                        color = Color.White.copy(alpha = 1f), // Ensures pure white
                        fontWeight = FontWeight.ExtraBold // Stronger emphasis
                    )
                }
            }

            // 🔹 Gradient Overlay (Left Dark, Right Faded)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xAAE91E63), Color(0x00FFE0F0))
                        )
                    )
                    .padding(12.dp)
            ) {
                // 🔹 Title & Price
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = offer.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "@ ${offer.price}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                // 🔹 Arrow Button
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(Color.White, shape = CircleShape)
                        .size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Arrow",
                        tint = Color(0xFFE91E63),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}


// ✅ Function to Calculate Remaining Time
fun calculateRemainingTime(targetTime: Long?): String {
    if (targetTime == null) return ""
    val currentTime = System.currentTimeMillis()
    val diff = targetTime - currentTime

    return if (diff <= 0) "Started" // Show "Started" when time reaches 0
    else {
        val hours = (diff / (1000 * 60 * 60)).toInt()
        val minutes = ((diff % (1000 * 60 * 60)) / (1000 * 60)).toInt()
        "${hours}h ${minutes}m"
    }
}

@Composable
fun MarqueeText() {
    val text = "LOOT OF THE YEAR  •  "
    val repeatedText = text.repeat(20) // Ensures seamless looping

    val infiniteTransition = rememberInfiniteTransition()
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -500f,  // Adjust based on text width
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color(0xFFFFE0F0))
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.offset(x = offsetX.dp)
        ) {
            BasicText(
                text = repeatedText,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Magenta
                )
            )
        }
    }
}

// ✅ Service Item
@Composable
fun ServiceItem(imageRes: Int, label: String) {
    Column(
        modifier = Modifier
            .width(110.dp)  // Adjust width for balance
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(12.dp), // Rounded corners
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F5)), // Light pink background
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Slight shadow
            modifier = Modifier.size(100.dp) // Square size
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(90.dp) // Ensure image fits well
                )
            }
        }
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun CustomBottomNavigationBar(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val selectedColor = Color(0xFF9B2242) // Pinkish-red selected color

    val items = listOf(
        BottomNavItem("Women", R.drawable.ic_launcher_foreground),
        BottomNavItem("Men", R.drawable.ic_launcher_foreground),
        BottomNavItem("Booking", R.drawable.ic_launcher_foreground),
        BottomNavItem("Account", R.drawable.ic_launcher_foreground)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Increased height for better circular look
            .background(Color.Transparent)
            .padding(bottom = 18.dp), // Padding from bottom
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            // 🔹 Bottom Navigation Bar (Rounded & Centered)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(83.dp)
                    .padding(horizontal = 8.dp) // ✅ Added left & right padding
                    .height(80.dp)
                    .clip(RoundedCornerShape(40.dp)) // Fully rounded
                    .shadow(10.dp, RoundedCornerShape(50.dp)), // Smooth shadow effect
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, item ->
                        if (index == 2) {
                            Spacer(modifier = Modifier.width(48.dp)) // Space for Floating Button
                        }
                        BottomNavItemView(
                            item = item,
                            isSelected = selectedIndex == index,
                            selectedColor = selectedColor
                        )
                        {
                            selectedIndex = index
                            if (selectedIndex == 1) {
                                navController.navigate("men")
                            }
                            if (selectedIndex == 0) {
                                navController.navigate("home")
                            }
                            if (selectedIndex == 3) {
                                navController.navigate("account")
                            }
                            if (selectedIndex == 2) {
                                navController.navigate("booking")
                            }
                        }
                    }
                }
            }

            // 🔹 Floating Action Button (Cart)
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .align(Alignment.Center)
//                    .offset(y = (-40).dp) // Floats above the nav bar
            ) {
                FloatingActionButton(
                    onClick = { /* Handle Cart Click */ },
                    shape = CircleShape,
                    containerColor = Color(0xFF9B2242),
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // 🔹 Small "+" Badge
//                Box(
//                    modifier = Modifier
//                        .size(22.dp)
//                        .background(Color(0xFF9B2242), CircleShape)
//                        .align(Alignment.TopEnd)
//                        .offset(x = 8.dp, y = (-4).dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Add,
//                        contentDescription = "Add",
//                        tint = Color.White,
//                        modifier = Modifier.size(14.dp)
//                    )
//                }
            }
        }
    }
}


// 🔹 Bottom Navigation Item Component
@Composable
fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(bottom = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = item.iconResId),
            contentDescription = item.label,
            tint = if (isSelected) selectedColor else Color.Gray,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = item.label,
            fontSize = 12.sp,
            color = if (isSelected) selectedColor else Color.Gray
        )
    }
}


// 🔹 Bottom Navigation Item Data Class
data class BottomNavItem(val label: String, val iconResId: Int)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedSearchBar(navController: NavController) {
    val services = listOf(
        "Waxing", "Facial", "Clean-Up", "De-Tan", "Bleach",
        "Mani-Pedi", "Threading", "Face Mask", "Scrub",
        "Bikini Wax", "Manicure", "Pedicure", "LED Facial", "Spa",
        "Body Polishing", "Head Massage"
    )

    var index by remember { mutableStateOf(0) }
    var text by remember { mutableStateOf("Search for ''") }

    LaunchedEffect(Unit) {
        while (true) {
            val word = services[index] // Get current service
            for (i in word.indices) {
                text = "Search for '${word.substring(0, i + 1)}'" // Update letter-by-letter
                delay(150) // Delay for animation effect
            }
            delay(1200) // Pause before changing the word
            index = (index + 1) % services.size // Move to next word
        }
    }

    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(text) }, // Letter-by-letter animated placeholder
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        shape = RoundedCornerShape(19.dp), // Fully rounded corners
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White, // Brighter background
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { navController.navigate("search") }, // Navigate to search screen
//        enabled = false // Disable direct text input
    )
}

@Composable
fun EliteBanner() {
    val texts = listOf("10% OFF on every booking", "Save ₹1000 in next 6 months")
    var currentTextIndex by remember { mutableStateOf(0) }

    // Automatically switch text every 3 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L) // Change text every 3 seconds
            currentTextIndex = (currentTextIndex + 1) % texts.size
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .clickable { }
            .padding(vertical = 11.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Elite",
                color = Color(0xFFD4AF37), // Gold color
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = texts[currentTextIndex],
                color = Color.White, // White color
                fontSize = 14.sp,
                modifier = Modifier.animateContentSize() // Smooth transition
            )
        }

        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Arrow",
            tint = Color.White,
            modifier = Modifier.size(14.dp) // Adjust size for alignment
        )
    }
}

@Composable
fun TrendingServicesSection() {
    Log.d("UI_LOG", "TrendingServicesSection Composable Called")

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0x80F3E5D8),  // Darker peach at the top with reduced opacity
//                        Color(0x80FDF1E4),  // Lighter peach with reduced opacity
//                        Color(0x80FFFFFF),   // Middle fully white with reduced opacity
//                        Color(0x80FFFFFF),   // Ensures full white section with reduced opacity
//                        Color(0x80FDF1E4),   // Fades back to peach with reduced opacity
//                        Color(0x80F3E5D8)    // Darker peach at the bottom with reduced opacity
//                    ),
//                    startY = 0f, // Starts from the top
//                    endY = Float.POSITIVE_INFINITY // Extends downward
//                )
//            ),
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF3E5D8).copy(alpha = 0.4f),
                        Color(0xFFFDF1E4).copy(alpha = 0.4f),
                        Color.White.copy(alpha = 0.3f),
                        Color(0x80FDF1E4).copy(alpha = 0.4f),
                        Color(0x80F3E5D8).copy(alpha = 0.4f),
                    )
                )
            ),
                horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("UI_LOG", "Column created with vertical gradient background")

        Spacer(modifier = Modifier.height(20.dp))
        Log.d("UI_LOG", "Spacer added (20.dp height)")

        Text(
            text = "Trending Services",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        Log.d("UI_LOG", "Text: Trending Services displayed")

        Spacer(modifier = Modifier.height(8.dp))
        Log.d("UI_LOG", "Spacer added (8.dp height)")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFFFFE6C7),
                            Color.Transparent
                        )
                    )
                )
        ) {
            Log.d("UI_LOG", "Box created with horizontal fading background")

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Log.d("UI_LOG", "Row for Location created")

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color(0xFFFF8C00),
                    modifier = Modifier.size(18.dp)
                )
                Log.d("UI_LOG", "Location Icon displayed")

                Spacer(modifier = Modifier.width(4.dp))
                Log.d("UI_LOG", "Spacer added (4.dp width)")

                Text("In Pimpri-Chinchwad", color = Color(0xFF666666), fontSize = 14.sp)
                Log.d("UI_LOG", "Text: In Pimpri-Chinchwad displayed")
            }
        }

        Spacer(modifier = Modifier.height(2.dp))
        Log.d("UI_LOG", "Spacer added (2.dp height)")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Log.d("UI_LOG", "Box created for white background middle section")

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Salon At Home",
                    fontSize = 16.sp,
                    color = Color(0xFFD81B60),
                    fontWeight = FontWeight.SemiBold
                )
                Log.d("UI_LOG", "Text: Salon At Home displayed")

//                Spacer(modifier = Modifier.height(2.dp))
                Log.d("UI_LOG", "Spacer added (2.dp height)")

                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(100.dp)
                        .background(Color(0xFFD81B60))
                )
                Log.d("UI_LOG", "Underlining Box created for Salon At Home")

//                Spacer(modifier = Modifier.height(8.dp))
                Log.d("UI_LOG", "Spacer added (8.dp height before carousel)")

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Log.d("UI_LOG", "Box created to hold ServiceCarousel()")
                    ServiceCarousel()
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))
        Log.d("UI_LOG", "Spacer added (22.dp height before next section)")
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServiceCarousel() {
    Log.d("UI_LOG", "ServiceCarousel Composable Called")

    val services = listOf(
        ServiceItem("Korean Glow Facial", "9 Steps Facial | Includes Free Silic...", "₹1149 • 1 hr 15 mins", R.drawable.ic_launcher_foreground),
        ServiceItem("Luxury Spa Therapy", "Deep Tissue | Aromatherapy", "₹1999 • 2 hrs", R.drawable.ic_launcher_foreground),
        ServiceItem("Hair Smoothening", "Keratin | Straightening | Rebonding", "₹2499 • 3 hrs", R.drawable.ic_launcher_foreground)
    )
    Log.d("UI_LOG", "Service list initialized with ${services.size} items")

    val pagerState = rememberPagerState { services.size }
    Log.d("UI_LOG", "Pager state initialized")

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) { page ->
            Log.d("UI_LOG", "HorizontalPager - displaying page index: $page")
            ServiceCard(services[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(services.size) { index ->
                Log.d("UI_LOG", "Dot indicator index: $index, current page: ${pagerState.currentPage}")
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 8.dp else 6.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) Color(0xFFD81B60) else Color.LightGray)
                )
            }
        }
    }
}


@Composable
fun ServiceCard(service: ServiceItem) {
    Log.d("UI_LOG", "ServiceCard Composable called for: ${service.name}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                Log.d("UI_LOG", "ServiceCard clicked for: ${service.name}")
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Log.d("UI_LOG", "Inside Card Row for: ${service.name}")

            Image(
                painter = painterResource(service.imageRes),
                contentDescription = service.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Log.d("UI_LOG", "Service Image shown for: ${service.name}")

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(service.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Log.d("UI_LOG", "Service name: ${service.name}")

                Text(service.details, fontSize = 12.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Log.d("UI_LOG", "Service details: ${service.details}")

                Text(service.price, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Log.d("UI_LOG", "Service price: ${service.price}")
            }

            OutlinedButton(
                onClick = {
                    Log.d("UI_LOG", "ADD button clicked for: ${service.name}")
                },
                border = BorderStroke(1.dp, Color(0xFFD81B60)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD81B60))
            ) {
                Text("ADD")
            }
        }
    }
}


// ✅ Chat with Expert Cosmetologist Section
@Composable
fun ChatWithCosmetologist() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { /* Navigate to chat */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color(0xFF3D7CC9)) // Blue background
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Chat with our", fontSize = 14.sp, color = Color.White)
                Text("Expert Cosmetologist", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Yellow)
                Text("at ₹100 for 24 hours", fontSize = 14.sp, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* Navigate to chat */ }, colors = ButtonDefaults.buttonColors(Color.White)) {
                    Text("AVAIL NOW", color = Color.Blue, fontWeight = FontWeight.Bold)
                }
            }
            Box(contentAlignment = Alignment.TopEnd) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Chat",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}

// ✅ Data Model for Services
data class ServiceItem(val name: String, val details: String, val price: String, val imageRes: Int)

@Composable
fun HydraTreatmentsUI() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("New Launch : Hydra Treatments", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(getHydraTreatments()) { treatment ->
                Column(modifier = Modifier.width(280.dp)) {
                    TreatmentCard(treatment)
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(treatment.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Start)
                        Text(treatment.subtitle, fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Start)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {
                                Text("\u20B9${treatment.discountedPrice}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "\u20B9${treatment.originalPrice}",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Button(
                                onClick = { /* Add to cart */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF0F0)),
                                modifier = Modifier
                                    .height(32.dp)
                                    .width(80.dp)
                            ) {
                                Text("ADD", color = Color(0xFFD90056), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TreatmentCard(treatment: Treatment) {
    val backgroundColors = listOf(Color(0xFFFCE8E6), Color(0xFFE8F5E9), Color(0xFFE3F2FD))
    val cardColor = backgroundColors[treatment.id % backgroundColors.size]

    Card(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(cardColor)) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp, top = 12.dp)
                ) {
                    Image(
                        painter = painterResource(id = treatment.logoRes),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        treatment.mainTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .width(140.dp)) {
                    Image(
                        painter = painterResource(id = treatment.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 60.dp, bottomStart = 60.dp))
                    )
                }
            }
        }
    }
}

// Sample data model
data class Treatment(
    val id: Int,
    val mainTitle: String,
    val title: String,
    val subtitle: String,
    val imageRes: Int,
    val logoRes: Int, // Added logo resource
    val discountedPrice: Int,
    val originalPrice: Int
)

// Sample data function
fun getHydraTreatments(): List<Treatment> {
    return listOf(
        Treatment(0, "Korean Glass Glow Ritual with Hydra Technology", "Korean Hydra Facial", "12 Steps With 7 Machines", R.drawable.aa, R.drawable.ic_launcher_foreground, 2399, 3999),
        Treatment(1, "Insta Collagen Boost", "14 Steps With Advanced Tech", "14 Steps With Advanced Tech", R.drawable.aa, R.drawable.ic_launcher_foreground, 2049, 3499)
    )
}

@Composable
fun TrendCard(treatment: Treatment_Updated) {
    Card(
        modifier = Modifier
            .width(145.dp)
            .height(145.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = treatment.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Logo at the top-left inside a white overlay
            Box(
                modifier = Modifier
//                    .padding(8.dp)
//                    .size(40.dp)
                    .align(Alignment.TopStart) // Position it at the top-left corner
                    .background(Color.Gray, shape = RoundedCornerShape(4.dp))
//                    .padding(horizontal = 8.dp, vertical = 4.dp)
//                    .background(Color.White, shape = CircleShape),
//                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = treatment.logoRes),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun HydraTreatmentsUI_Updated() {
    val categories = listOf("Hydra Facials", "Waxing", "Facial", "Stress Relief")
    var selectedCategory by remember { mutableStateOf(categories.first()) }

    val services = mapOf(
        "Hydra Facials" to listOf(
            Treatment_Updated(0,  "Korean Hydra Facial fgetdh dhdhd","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2399,40, 3999),
            Treatment_Updated(1,  "14 Steps With Advanced Tech","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2049, 40,3499),
            Treatment_Updated(2,  "14 Steps With Advanced Tech","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2049, 40,3499),
            Treatment_Updated(3,  "14 Steps With Advanced Tech","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2049, 40,3499)
    ),
        "Waxing" to listOf(
            Treatment_Updated(0,  "14 Steps With Advanced Tech","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2399, 40,3999),
            Treatment_Updated(1,  "Korean Hydra Facial","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2049,40, 3499)
        ),
        "Facial" to listOf(
            Treatment_Updated(0,  "Korean Hydra Facial fgetdh dhdhd","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2399,40, 3999),
            Treatment_Updated(1,  "14 Steps With Advanced Tech","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2049,40, 3499)
        ),
        "Stress Relief" to listOf(
            Treatment_Updated(0,  "Korean Hydra Facial fgetdh dhdhd","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2399,40, 3999),
            Treatment_Updated(1,  "14 Steps With Advanced Tech","1 hr 40 min",  R.drawable.aa, R.drawable.ic_launcher_foreground, 2049,40, 3499)
        ),
    )
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Trending Near You", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(categories) { category ->
                Button(
                    onClick = { selectedCategory = category },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == category) Color.Black else Color.Gray
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(category, color = Color.White)
                }
            }
        }
        val selectedTreatments = services[selectedCategory] ?: emptyList()
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(selectedTreatments) { treatment ->
                Column(modifier = Modifier.width(150.dp)) {
                    TrendCard(treatment)
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            treatment.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp), // ✅ Fixed height for all titles
                            maxLines = 2,
//                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            "${treatment.duration}",
                            fontSize = 12.sp,
                            color = Color.Gray,
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "\u20B9${treatment.discountedPrice}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "\u20B9${treatment.originalPrice}",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "${treatment.discountPercentage}% OFF",
                                    fontSize = 12.sp,
                                    color = Color(0xFF4CAF50), // Green color for discount text
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { /* Add to cart */ },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFF0F0)
                            ),
                            modifier = Modifier
//                                .fillMaxWidth()
                                .width(150.dp)
                                .height(36.dp)
//                                .clip(RoundedCornerShape(2.dp))
                        ) {
                            Text("Add To Cart", color = Color(0xFFD90056), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// Sample data model
data class Treatment_Updated(
    val id: Int,
    val title: String,
    val duration: String,
    val imageRes: Int,
    val logoRes: Int, // Added logo resource
    val discountedPrice: Int,
    val discountPercentage: Int,
    val originalPrice: Int
)

@Composable
fun HorizontalScrollCardWithDots() {
    // List of images for each card (replace with your actual image resource IDs)
    val images = listOf(
        R.drawable.aa, // Replace with actual image resource IDs
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_background,
        R.drawable.aa,
        R.drawable.ic_launcher_background,
    )

    // Create a PagerState to track the current page for dots
    val pagerState = rememberPagerState(pageCount = {images.size})

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Create HorizontalPager to enable automatic horizontal scrolling
        HorizontalPager(
            state = pagerState,
//            pageCount = images.size,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            // Card with image for each index
            Card(
                modifier = Modifier
                    .width(385.dp)
                    .padding(horizontal = 16.dp)
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Card Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Create a Row to show dots (indicating page position) below the cards
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Create dots for each page/item in the HorizontalPager
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) Color.Blue else Color.LightGray
                        )
                )
            }
        }
    }

    // Automatically scroll to next page every 1 second (optional)
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000) // Wait for 1 second
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % images.size)
        }
    }
}

@Composable
fun SalonPackageList() {
    val packages = listOf(
        SalonPackage(
            title = "All in One Salon Care",
            heading = "Make Your Own Package",
            details = "Waxing: Full Arms, Full Legs & Underarms (Rica)\n\n\n" +
                    "Premium Facial: Korean Glow Facial\n\n\n" +
                    "Manicure & Pedicure: Ice Cream Mani & Pedi\n\n\n" +
                    "Facial Hair Removal: Eyebrows (Threading)\n\n",
            discountedPrice = 2780,
            originalPrice = 6148,
            discountPercentage = 54,
            duration = "4 hrs 5 mins"
        ),
        SalonPackage(
            title = "Wax & Glow",
            heading = "Exclusive Beauty Deal",
            details = "Waxing: Wax and Glow\n\n\n" +
                    "Facial: Gold Glow Facial\n\n\n" +
                    "Manicure & Pedicure: Deluxe Mani-Pedi\n\n\n" +
                    "Eyebrow Shaping\n\n",
            discountedPrice = 1999,
            originalPrice = 4499,
            discountPercentage = 50,
            duration = "3 hrs 30 mins"
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Make Your Own Package", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(8.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth()
//            .padding(start = 10.dp)
    ) {
        items(packages) { salonPackage ->
            SalonPackageCard(salonPackage)
        }
    }
}

@Composable
fun SalonPackageCard(salonPackage: SalonPackage) {
    Card(
        modifier = Modifier
            .width(380.dp)
            .height(450.dp) // Fixed height for uniformity
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp), // Added left padding
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Ensures all content respects the fixed height
                .background(Color.White)
        ) {
            // Custom Header with Discount Tag
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_agenda),
                        contentDescription = "Package Icon",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = salonPackage.heading,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Discount Tag
            Box(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .offset(y = (-10).dp)
                    .background(Color.Green, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "${salonPackage.discountPercentage}% OFF",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Title with "Edit Package" Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = salonPackage.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                OutlinedButton(
                    onClick = { /* Handle Edit Package Click */ },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Edit Package",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(17.dp))
            // Styled Package Details Section with Fixed Size
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp) // Ensures the section is always the same size
                    .padding(horizontal = 16.dp)
                    .background(
                        Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(8.dp)
                    ) // Light Gray Background
                    .padding(12.dp)
            ) {
                Column {
                    val detailsList = salonPackage.details.split("\n\n\n") // Splitting details into list items

                    detailsList.forEach { detail ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = "•", // Bullet point
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(end = 6.dp)
                            )
                            Text(
                                text = detail,
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Section - Styled Like Reference Image
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // ADD Button
                OutlinedButton(
                    onClick = { /* Add action */ },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD81B60)), // Pink color
                    border = BorderStroke(1.dp, Color(0xFFD81B60)), // Pink border
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "ADD", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                // Price & Duration Section
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Discounted Price
                    Text(
                        modifier = Modifier.padding(start = 14.dp),
                        text = "₹${salonPackage.discountedPrice}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Original Price (Strikethrough)
                    Text(
                        text = "₹${salonPackage.originalPrice}",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough // Strikethrough effect
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Duration with Clock Icon
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Time Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = salonPackage.duration,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

data class SalonPackage(
    val title: String,
    val heading: String, // Now each package has a different heading
    val details: String,
    val discountedPrice: Int,
    val originalPrice: Int,
    val discountPercentage: Int,
    val duration: String
)

@Composable
fun ChatWithCosmetologist_Updated() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .width(385.dp)
            .height(200.dp)
            .clickable { /* Navigate to chat */ },
        colors = CardDefaults.cardColors(Color(0xFF3D7CC9)) // Blue background
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
                Image(
                    painter = painterResource(id = R.drawable.aa),
                    contentDescription = "Card Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

@Composable
fun SalonHomeScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Salon At Home For Women",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ScrollableServiceGrid()
    }
}

@Composable
fun ScrollableServiceGrid() {
    val services = listOf(
        "Waxing" to R.drawable.ic_launcher_background,
        "Facial" to R.drawable.ic_launcher_background,
        "Mani-Pedi" to R.drawable.ic_launcher_background,
        "Clean-Up" to R.drawable.ic_launcher_background,
        "De-Tan / Bleach" to R.drawable.ic_launcher_background,
        "Body Polish" to R.drawable.ic_launcher_background
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        services.chunked(2).forEach { columnItems ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                columnItems.forEach { (title, image) ->
                    ServiceCard(title, image, Modifier.width(145.dp))
                }
            }
        }
    }
}

@Composable
fun ServiceCard(title: String, imageRes: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(8.dp))
//            .width(150.dp)
            .height(145.dp)
            .clickable {},
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ChatWithCosmetologist_UpdatedNew() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .width(385.dp)
            .height(200.dp)
            .clickable { /* Navigate to chat */ },
        colors = CardDefaults.cardColors(Color(0xFF3D7CC9)), // Blue background
        shape = RectangleShape // Removes rounded corners
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.aa),
                contentDescription = "Card Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SalonHomeScreen_Updated() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Spa At Home For Women",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ScrollableServiceGrid_Updated()
    }
}

@Composable
fun ScrollableServiceGrid_Updated() {
    val services = listOf(
        "Waxing" to R.drawable.ic_launcher_background,
        "Facial" to R.drawable.ic_launcher_background,
        "Mani-Pedi" to R.drawable.ic_launcher_background,
        "Clean-Up" to R.drawable.ic_launcher_background,
        "De-Tan / Bleach" to R.drawable.ic_launcher_background,
        "Body Polish" to R.drawable.ic_launcher_background
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        services.chunked(2).forEach { columnItems ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                columnItems.forEach { (title, image) ->
                    ServiceCard(title, image, Modifier.width(145.dp))
                }
            }
        }
    }
}

@Composable
fun VideoListScreen_Women(navController: NavController) {
    val selectedVideo = remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Stories From The Best",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(videoList_Women) { video ->
                VideoThumbnail_Women(
                    videoId = video.videoId,
                    title = video.title,
                    subtitle = video.subtitle
                ) {
                    selectedVideo.value = video.videoId
                }
            }
        }

        // ✅ Show Video Player when a video is selected
        selectedVideo.value?.let { videoId ->
            YouTubeFullScreenPlayer_Women(videoId, navController)
        }
    }
}

@Composable
fun VideoThumbnail_Women(videoId: String, title: String, subtitle: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .clickable { onClick() }
    ) {
        // Load YouTube Thumbnail Image
        AsyncImage(
            model = "https://img.youtube.com/vi/$videoId/0.jpg",
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark Gradient Overlay at Bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xFF9B2242))))
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = subtitle, fontSize = 12.sp, color = Color.White)
        }

        // Play Button in Center
        Icon(
            imageVector = Icons.Default.PlayCircle,
            contentDescription = "Play Video",
            tint = Color.White,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center)
        )
    }
}
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubeFullScreenPlayer_Women(videoId: String, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Ensure background is black
    ) {
        // ✅ Full-screen WebView for YouTube video
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.mediaPlaybackRequiresUserGesture = false // Auto-play support
                    loadUrl("https://www.youtube.com/embed/$videoId?autoplay=1&controls=1&playsinline=1")
                }
            }
        )

        // ✅ Close Button (Top Left)
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart) // Top left corner
                .background(Color.Black.copy(alpha = 0.7f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Video",
                tint = Color.White
            )
        }
    }
}



data class VideoItem_Women(val videoId: String, val title: String, val subtitle: String)

val videoList_Women = listOf(
    VideoItem_Women("XxXxXxX", "Rohit Roy", "On Safe Grooming"),
    VideoItem_Women("YyYyYyY", "Vindu Dara Singh", "On Chilling at Home"),
    VideoItem_Women("ZzZzZzZ", "Another Speaker", "On Fitness Tips")
)
