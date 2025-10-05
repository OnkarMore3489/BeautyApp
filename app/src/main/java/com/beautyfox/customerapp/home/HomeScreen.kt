package com.beautyfox.customerapp.home

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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.LocationOn
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
import com.beautyfox.customerapp.R
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow

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
                    modifier = Modifier
                        .clickable { navController.navigate("location") }
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
        Spacer(modifier = Modifier.height(28.dp)) // ✅ Add Space Between Both Sections
        TrendingServicesSection()
        Spacer(modifier = Modifier.height(28.dp))
        ChatWithCosmetologist()
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
        BottomNavItem("Home", R.drawable.ic_launcher_foreground),
        BottomNavItem("Offers", R.drawable.ic_launcher_foreground),
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
        colors = TextFieldDefaults.colors(
//            containerColor = Color.White, // Brighter background
//            focusedBorderColor = MaterialTheme.colorScheme.primary,
//            unfocusedBorderColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { navController.navigate("search") }, // Navigate to search screen
        enabled = false // Disable direct text input
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x80F3E5D8),  // Darker peach at the top with reduced opacity
                        Color(0x80FDF1E4),  // Lighter peach with reduced opacity
                        Color(0x80FFFFFF),   // Middle fully white with reduced opacity
                        Color(0x80FFFFFF),   // Ensures full white section with reduced opacity
                        Color(0x80FDF1E4),   // Fades back to peach with reduced opacity
                        Color(0x80F3E5D8)    // Darker peach at the bottom with reduced opacity
                    ),
                    startY = 0f, // Starts from the top
                    endY = Float.POSITIVE_INFINITY // Extends downward
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp)) // Increased spacing from top

        // 🔹 Title: "Trending Services"
        Text(
            text = "Trending Services",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔹 Location Row (Fading Background)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,  // Fades from left
                            Color(0xFFFFE6C7), // Stronger peach in center
                            Color.Transparent   // Fades to right
                        )
                    )
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 12.dp, vertical = 6.dp) // Proper spacing
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color(0xFFFF8C00),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("In Pimpri-Chinchwad", color = Color(0xFF666666), fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(2.dp)) // Adjusted spacing

        // ✅ White Background for Middle Section (Salon + Cards)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White) // Ensures middle section is fully white
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // 🔹 "Salon At Home" Text (Underlined)
                Text(
                    text = "Salon At Home",
                    fontSize = 16.sp,
                    color = Color(0xFFD81B60),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(100.dp)
                        .background(Color(0xFFD81B60))
                )

                Spacer(modifier = Modifier.height(8.dp)) // Better spacing for closeness

                // 🔹 Scrollable Service Card (Now Closer to Text)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Adjust height if needed
                        .padding(horizontal = 16.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    ServiceCarousel()
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp)) // Space before next section
    }
}


// Scrollable Cards with Dots
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServiceCarousel() {
    val services = listOf(
        ServiceItem("Korean Glow Facial", "9 Steps Facial | Includes Free Silic...", "₹1149 • 1 hr 15 mins", R.drawable.ic_launcher_foreground),
        ServiceItem("Luxury Spa Therapy", "Deep Tissue | Aromatherapy", "₹1999 • 2 hrs", R.drawable.ic_launcher_foreground),
        ServiceItem("Hair Smoothening", "Keratin | Straightening | Rebonding", "₹2499 • 3 hrs", R.drawable.ic_launcher_foreground)
    )

    val pagerState = rememberPagerState { services.size }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) { page ->
            ServiceCard(services[page])
        }

        // 🔹 Dot Indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(services.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 8.dp else 6.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) Color(0xFFD81B60) else Color.LightGray) // Magenta active dot
                )
            }
        }
    }
}

// Service Card Component
@Composable
fun ServiceCard(service: ServiceItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { /* Handle click */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(service.imageRes),
                contentDescription = service.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(service.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(service.details, fontSize = 12.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(service.price, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = { /* Handle add */ },
                border = BorderStroke(1.dp, Color(0xFFD81B60)), // Magenta border
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