import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.GameInspiration.R
import com.example.gameinspiration.ui.theme.GameInspirationTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewmodel.compose.viewModel as viewModelCompose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameInspirationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InspirationApp()
                }
            }
        }
    }
}

val lobsterFontFamily = FontFamily(
    Font(R.font.lobster, FontWeight.Normal)
)

val acmeFontFamily = FontFamily(
    Font(R.font.acme, FontWeight.Normal)
)

class InspirationViewModel : ViewModel() {
    private val _selectedTip = MutableStateFlow<Tip?>(null)
    val selectedTip: StateFlow<Tip?> get() = _selectedTip

    private val _comments = MutableStateFlow(TextFieldValue())
    val comments: StateFlow<TextFieldValue> get() = _comments

    private val _commentList = mutableStateListOf<String>()
    val commentList: List<String> get() = _commentList

    fun onTipSelected(tip: Tip) {
        _selectedTip.value = tip
    }

    fun onCommentsChanged(newComments: TextFieldValue) {
        _comments.value = newComments
    }

    fun addComment(comment: String) {
        _commentList.add(comment)
    }

    fun clearSelectedTip() {
        _selectedTip.value = null
    }
}



@Composable
fun InspirationApp(viewModel: InspirationViewModel = viewModelCompose()) {
    val selectedTip by viewModel.selectedTip.collectAsState()
    val comments by viewModel.comments.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (selectedTip == null) {
            TipList(viewModel)
        } else {
            DetailPage(viewModel, comments)
        }
    }
}

@Composable
fun TipList(viewModel: InspirationViewModel) {
    val tips = listOf(
        Tip(1, "Embark on an insane journey in Outer Wilds, a living solar system packed with jaw-dropping moments. Play this video game for an unparalleled experience.",
            "Genre: Action-Adventure, Exploration\nPlatforms: Microsoft Windows, Xbox One, PlayStation 4\n", R.drawable.outerwilds),

        Tip(2, "Experience the absolute king of one-sitting games in Journey. It's a world-class adventure from start to finish that transcends traditional gaming.",
            "Genre: Adventure, Art, Exploration\nPlatforms: PlayStation 3, PlayStation 4, Microsoft Windows, iOS\n", R.drawable.journey),

        Tip(3, "Dive into the well-written and flat-out good world of Firewatch. It's a game about loneliness, offering an insightful and interesting story.",
            "Genre: Adventure, Mystery\nPlatforms: Microsoft Windows, macOS, Linux, PlayStation 4, Xbox One, Nintendo Switch\n", R.drawable.firewatch),

        Tip(4, "Explore a story-focused, first-person game in What Remains of Edith Finch. A must-play for those who enjoyed games like Dear Esther or Gone Home.",
            "Genre: Adventure, Interactive Story\nPlatforms: Microsoft Windows, PlayStation 4, Xbox One, Nintendo Switch\n", R.drawable.edithfinch),

        Tip(5, "Undertale is a masterpiece about games or with elements of satire. Experience its amazing narrative, especially if you love RPGs.",
            "Genre: Role-Playing, Indie\nPlatforms: Microsoft Windows, macOS, Linux, PlayStation 4, PlayStation Vita, Nintendo Switch\n", R.drawable.undertale),

        Tip(6, "Play Papers Please, a game growing in relevance, addressing global immigration issues. It's a timely experience with impactful decision-making.",
            "Genre: Simulation, Puzzle\nPlatforms: Microsoft Windows, macOS, Linux, iOS, PlayStation Vita\n", R.drawable.papersplease),

        Tip(7, "Dive into the breathtaking underwater exploration of Abzu. Though reminiscent of Journey, it's a must-play for oceanic adventure lovers.",
            "Genre: Adventure, Exploration\nPlatforms: Microsoft Windows, PlayStation 4, Xbox One, Nintendo Switch\n", R.drawable.abzu),

        Tip(8, "Indulge in the satisfying gameplay of Donut County. Despite its short length, it charms with witty characters and a playful journey.",
            "Genre: Puzzle, Indie\nPlatforms: Microsoft Windows, macOS, iOS, PlayStation 4, Xbox One, Nintendo Switch\n", R.drawable.donutcounty),

        Tip(9, "Experience the endearing journey of two brothers in search of a cure in this heartwarming and fresh game.",
            "Genre: Adventure, Puzzle\nPlatforms: Microsoft Windows, PlayStation 3, Xbox 360, iOS, Android\n", R.drawable.brothers),

        Tip(10, "Enter the captivating and haunting world of Limbo. It's a sleek, dark, and unforgettable 2D puzzle platformer.",
            "Genre: Puzzle-Platformer\nPlatforms: Microsoft Windows, macOS, Linux, PlayStation 3, Xbox 360, PlayStation Vita, iOS, Android, Nintendo Switch\n", R.drawable.limbo),

        Tip(11, "Embark on a solo adventure inspired by Iceland's breathtaking landscapes in Spirit of the North. Solve puzzles and discover a ruined land with a unique design.",
            "Genre: Adventure, Puzzle\nPlatforms: Microsoft Windows, PlayStation 4, PlayStation 5\n", R.drawable.spiritofthenorth),

        Tip(12, "Thirsty Suitors explores culture, relationships, and family dynamics. Navigate through cinematic and turn-based combat scenes in this unique narrative.",
            "Genre: Narrative, Turn-Based Combat\nPlatforms: Microsoft Windows\n", R.drawable.thirstysuitors),

        Tip(13, "Master world-jumping mechanics in Cocoon, a puzzle adventure by the lead designer of LIMBO and INSIDE. Explore diverse biomes and solve intricate puzzles.",
            "Genre: Puzzle, Adventure\nPlatforms: Microsoft Windows\n", R.drawable.cocoon),

        Tip(14, "Roam the neon-lit streets as a third-person cat in Stray. Encounter unexpected dangers and interact with the mysterious inhabitants of this cyber city.",
            "Genre: Adventure\nPlatforms: PlayStation 4, PlayStation 5, Microsoft Windows\n", R.drawable.stray),

        Tip(15, "Explore diverse and bleak environments in the engaging puzzle game INSIDE. Its captivating graphics and dark theme create a unique gaming experience.",
            "Genre: Puzzle-Platformer\nPlatforms: Microsoft Windows, PlayStation 4, Xbox One, iOS, Nintendo Switch\n", R.drawable.inside),

        Tip(16, "Control the story with real-life blinks in Before Your Eyes, an emotional first-person narrative adventure. Immerse yourself in a world of memories.",
            "Genre: Adventure, Narrative\nPlatforms: Microsoft Windows\n", R.drawable.beforeyoureyes),

        Tip(17, "Unravel a mysterious disappearance in the first-person story-driven mystery, The Vanishing of Ethan Carter. Dive into a visually stunning world.",
            "Genre: Adventure, Mystery\nPlatforms: Microsoft Windows, PlayStation 4, Xbox One, Nintendo Switch\n", R.drawable.ethancarter),

        Tip(18, "Step into the supernatural thriller Oxenfree, where friends open a ghostly rift. Navigate through an island party gone wrong in this gripping narrative.",
            "Genre: Adventure\nPlatforms: Microsoft Windows, macOS, Linux, PlayStation 4, Xbox One, Nintendo Switch, iOS, Android\n", R.drawable.oxenfree),

        Tip(19, "Uncover dark secrets inscribed upon cards in Inscryption, a unique blend of deckbuilding, puzzles, and psychological horror.",
            "Genre: Deckbuilding, Puzzle, Horror\nPlatforms: Microsoft Windows\n", R.drawable.inscryption),

        Tip(20, "Descend into the dark dungeons of Fear&Hunger in this horror dungeon crawler. Experience a hybrid of survival horror and dungeon crawler genres.",
            "Genre: Horror, Dungeon Crawler\nPlatforms: Microsoft Windows\n", R.drawable.fearnhunger),

        Tip(21, "Play as an Indian mother in Venba, a narrative-driven cooking game. Cook, uncover lost recipes, and discover new things in this family-themed story.",
            "Genre: Narrative, Cooking\nPlatforms: Microsoft Windows\n", R.drawable.venba),

        Tip(22, "Embark on a captivating puzzle adventure in I Am Dead, exploring the theme of the afterlife. Unveil mysteries with puzzles in this unique experience.",
            "Genre: Puzzle-Adventure\nPlatforms: Microsoft Windows, macOS, Nintendo Switch\n", R.drawable.iamdead),

        Tip(23, "Arrange items in delightful ways in A Little to the Left, a satisfying puzzle game. Watch out for mischievous cats keeping an eye on your organization skills.",
            "Genre: Puzzle\nPlatforms: Microsoft Windows\n", R.drawable.alittletotheleft),

        Tip(24, "Challenge your senses in Viewfinder, a game that redefines reality with a disposable camera. Uncover mysteries and enjoy fascinating experiences.",
            "Genre: Puzzle, Reality\nPlatforms: Microsoft Windows\n", R.drawable.viewfinder),

        Tip(25, "Create a zen puzzle experience in Unpacking, where you pull possessions out of boxes and arrange them in a new home. Learn more about the life you're unpacking.",
            "Genre: Puzzle\nPlatforms: Microsoft Windows, macOS, Linux\n", R.drawable.unpacking),

        Tip(26, "Solve puzzles, communicate effectively, and find your way out of an abandoned castle in We Were Here, a cooperative puzzle adventure.",
            "Genre: Puzzle-Adventure\nPlatforms: Microsoft Windows\n", R.drawable.wewerehere),

        Tip(27, "Explore the mind-bending world of The Stanley Parable, a first-person exploration game where choices shape the narrative.",
            "Genre: Adventure, Indie\nPlatforms: Microsoft Windows, macOS, Linux\n", R.drawable.stanleyparable),

        Tip(28, "Survive in a post-apocalyptic world in 60 Seconds! Reatomized. Scavenge for resources, make tough decisions, and possibly live to tell the tale.",
            "Genre: Strategy, Simulation\nPlatforms: Microsoft Windows, macOS, Linux\n", R.drawable.sixtyseconds),

        Tip(29, "Manage the afterlife in Spiritfarer, a peaceful game where you ferry the deceased. Cultivate, mine, fish, and create lasting memories in this emotional journey.",
            "Genre: Simulation, Adventure\nPlatforms: Microsoft Windows, macOS, Linux, PlayStation 4, Xbox One, Nintendo Switch\n", R.drawable.spiritfarer),

        Tip(30, "Relive the legendary Swords and Sandals turn-based combat game series. Swords and Sandals is not just a game; it's nostalgia, a part of life.",
            "Genre: Turn-Based Combat\nPlatforms: Microsoft Windows\n", R.drawable.swordsandsandals)


    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(tips.size) { index ->
            InspirationCard(tips[index], onItemSelected = { viewModel.onTipSelected(it) })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun InspirationCard(tip: Tip, onItemSelected: (Tip) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .background(MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Day ${tip.day}",
                style = MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontFamily = lobsterFontFamily
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tip.quote,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = acmeFontFamily
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = tip.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onItemSelected(tip) },
                modifier = Modifier
                    .offset(x = (120).dp,
                        y = (0).dp)

            ) {
                Text(
                    text = "Details",
                    fontFamily = acmeFontFamily
                )
            }
        }
    }
}

@Composable
fun DetailPage(viewModel: InspirationViewModel, comments: TextFieldValue) {
    val selectedTip by viewModel.selectedTip.collectAsState()
    val commentList = viewModel.commentList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        selectedTip?.let { tip ->
            Text(
                text = "Day ${tip.day} Details",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontFamily = lobsterFontFamily
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tip.quote,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = acmeFontFamily
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display additional details
            Text(
                text = tip.details,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = acmeFontFamily
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = tip.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(commentList) { comment ->
                    Text(text = comment)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = comments,
                onValueChange = { viewModel.onCommentsChanged(it) },
                label = { Text("Add a comment") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = { viewModel.clearSelectedTip() }
                ) {
                    Text(text = "Back")
                }

                Button(
                    onClick = {
                        viewModel.addComment(comments.text)
                        viewModel.onCommentsChanged(TextFieldValue())
                    }
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Send")
                }


            }
        }
    }
}



data class Tip(val day: Int, val quote: String, val details: String, val imageRes: Int)


@Composable
@Preview(showBackground = true)
fun PreviewInspirationApp() {
    InspirationApp()
}
