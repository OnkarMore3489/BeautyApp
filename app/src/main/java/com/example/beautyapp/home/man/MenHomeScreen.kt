package com.example.beautyapp.home.man

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage

@Composable
fun MenHomeScreen(navController: NavController) {
    val listState = rememberLazyListState()

    val hasScrolled = listState.firstVisibleItemIndex > 0 ||
            listState.firstVisibleItemScrollOffset > 0

    val targetColor = if (hasScrolled) Color.Blue else Color.Red

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        label = "bgColor"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 🔹 Scrollable content
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Image(
                    painter = rememberAsyncImagePainter("https://yourimageurl.com/banner.png"),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(385.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(120.dp)) }
            item { MenMarqueeText() }

            item {
                Text(
                    text = "Everything That You Need",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    MenServiceItem(R.drawable.ic_launcher_foreground, "Salon At Home")
                    MenServiceItem(R.drawable.ic_launcher_foreground, "Pre Bridal")
                    MenServiceItem(R.drawable.ic_launcher_foreground, "Advance")
                }
            }

            item { Spacer(modifier = Modifier.height(15.dp)) }
            item { MenEliteBanner() }
            item { Spacer(modifier = Modifier.height(28.dp)) }
            item { MenTrendingServicesSection() }
            item { Spacer(modifier = Modifier.height(28.dp)) }
            item { MenChatWithCosmetologist() }
            item { Spacer(modifier = Modifier.height(28.dp)) }
            item { VideoListScreen(navController) }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }

        // 🔹 Sticky Header (Location + Search + Toggle)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(animatedColor)
                .zIndex(1f)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() }
                    ) { navController.navigate("location") },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Dhanori",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow",
                            modifier = Modifier.size(30.dp)
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
                    colors = ButtonDefaults.buttonColors(Color(0xFFD4AF37)),
                    shape = CircleShape
                ) {
                    Text("Buy Elite", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Toggle Button + Search Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp)
            ) {
                Button(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(45.dp)
                ) {
                    Text("Women", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(modifier = Modifier.weight(1f)) {
                    MenAnimatedSearchBar(navController)
                }
            }
        }
    }
}



@Composable
fun MenMarqueeText() {
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
fun MenServiceItem(imageRes: Int, label: String) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenAnimatedSearchBar(navController: NavController) {
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
//            .clickable { navController.navigate("search") }, // Navigate to search screen
        .clickable(
            indication = rememberRipple(), // 🔹 Ripple effect on click
    interactionSource = remember { MutableInteractionSource() } // 🔹 Tracks touch events
    ) {
        navController.navigate("search")
    },
        enabled = false // Disable direct text input
    )
}

@Composable
fun MenEliteBanner() {
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
//            .clickable { }
            .clickable(
                indication = rememberRipple(), // 🔹 Ripple effect on click
                interactionSource = remember { MutableInteractionSource() } // 🔹 Tracks touch events
            ) {
            }
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
fun MenTrendingServicesSection() {
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
                    MenServiceCarousel()
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp)) // Space before next section
    }
}


// Scrollable Cards with Dots
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenServiceCarousel() {
    val services = listOf(
        MenServiceItem("Korean Glow Facial", "9 Steps Facial | Includes Free Silic...", "₹1149 • 1 hr 15 mins", R.drawable.ic_launcher_foreground),
        MenServiceItem("Luxury Spa Therapy", "Deep Tissue | Aromatherapy", "₹1999 • 2 hrs", R.drawable.ic_launcher_foreground),
        MenServiceItem("Hair Smoothening", "Keratin | Straightening | Rebonding", "₹2499 • 3 hrs", R.drawable.ic_launcher_foreground)
    )

    val pagerState = rememberPagerState { services.size }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) { page ->
            MenServiceCard(services[page])
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
fun MenServiceCard(service: MenServiceItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
//            .clickable { /* Handle click */ },
        .clickable(
            indication = rememberRipple(), // 🔹 Ripple effect on click
    interactionSource = remember { MutableInteractionSource() } // 🔹 Tracks touch events
    ) {
    },
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
fun MenChatWithCosmetologist() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
//            .clickable { /* Navigate to chat */ },
        .clickable(
            indication = rememberRipple(), // 🔹 Ripple effect on click
    interactionSource = remember { MutableInteractionSource() } // 🔹 Tracks touch events
    ) {
    },
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
data class MenServiceItem(val name: String, val details: String, val price: String, val imageRes: Int)

@Composable
fun VideoListScreen(navController: NavController) {
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
            items(videoList) { video ->
                VideoThumbnail(
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
            YouTubeFullScreenPlayer(videoId, navController)
        }
    }
}

@Composable
fun VideoThumbnail(videoId: String, title: String, subtitle: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
//            .clickable { onClick() }
            .clickable(
                indication = rememberRipple(), // 🔹 Ripple effect on click
                interactionSource = remember { MutableInteractionSource() } // 🔹 Tracks touch events
            ) {
                onClick()
            },
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
fun YouTubeFullScreenPlayer(videoId: String, navController: NavController) {
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



data class VideoItem(val videoId: String, val title: String, val subtitle: String)

val videoList = listOf(
    VideoItem("XxXxXxX", "Rohit Roy", "On Safe Grooming Session"),
    VideoItem("YyYyYyY", "Vindu Dara Singh", "On Chilling at Home"),
    VideoItem("ZzZzZzZ", "Another Speaker", "On Fitness Tips")
)
