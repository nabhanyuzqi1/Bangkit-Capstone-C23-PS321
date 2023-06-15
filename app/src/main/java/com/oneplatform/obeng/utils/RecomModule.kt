

data class PredictionRequest(
    val user_id: Int,
    val needed_ban: Int,
    val needed_mesin: Int,
    val needed_bodi: Int,
    val needed_interior: Int,
    val needed_oli: Int
)

data class PredictionResponse(
    val predictedTechniciansID: Int,
    val predictedRating: Int,
    val recommendedTechniciansID: String
)

