@Entity(
    tableName = "moods",
    foreignKeys = [ForeignKey(
        entity = Pet::class,
        parentColumns = ["id"],
        childColumns = ["petId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("petId")]
)
data class Mood(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val petId: Int,
    val mood: String, // happy, sad, neutral, etc.
    val date: Long    // timestamp
)
