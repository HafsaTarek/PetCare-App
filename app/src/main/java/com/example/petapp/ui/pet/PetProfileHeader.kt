import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.petapp.data.entity.Pet

@Composable
fun PetProfileHeader(pet: Pet) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (!pet.imageUrl.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(pet.imageUrl),
                contentDescription = "${pet.name} image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = pet.name, style = MaterialTheme.typography.titleLarge)
        Text(text = "${pet.type}, ${pet.age} years old", style = MaterialTheme.typography.bodyMedium)
        Text(text = pet.description, style = MaterialTheme.typography.bodySmall)
    }
}
