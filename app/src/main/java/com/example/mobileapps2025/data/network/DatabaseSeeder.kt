//package com.example.mobileapps2025.data.network
//
//import android.util.Log
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//
//object DatabaseSeeder {
//
//    suspend fun run() {
//        val db = FirebaseFirestore.getInstance()
//        val collection = db.collection("artists")
//
//        val artistsToUpload = listOf(
//            ArtistNetworkModel(
//                name = "Linkin Park", genre = "Rock",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Linkin_Park_in_2017.jpg/800px-Linkin_Park_in_2017.jpg",
//                descriptionEn = "Legendary alternative rock band from California.",
//                descriptionRu = "Легендарная альтернативная рок-группа из Калифорнии.",
//                descriptionBg = "Легендарна алтернативна рок група от Калифорния.",
//                concertCities = "Los Angeles, London, Berlin"
//            ),
//            ArtistNetworkModel(
//                name = "Eminem", genre = "Hip-Hop",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/44/Eminem_-_2011_V_Festival.jpg",
//                descriptionEn = "One of the greatest and most influential rappers of all time.",
//                descriptionRu = "Один из величайших и самых влиятельных рэперов всех времен.",
//                descriptionBg = "Един от най-великите и влиятелни рапъри на всички времена.",
//                concertCities = "Detroit, New York, Tokyo"
//            ),
//            ArtistNetworkModel(
//                name = "Rihanna", genre = "Pop / R&B",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c2/Rihanna_Fenty_2018.png",
//                descriptionEn = "Global pop icon and successful businesswoman.",
//                descriptionRu = "Глобальная поп-икона и успешная бизнесвумен.",
//                descriptionBg = "Глобална поп икона и успешна бизнесдама.",
//                concertCities = "Paris, Miami, Sofia"
//            ),
//            ArtistNetworkModel(
//                name = "Metallica", genre = "Heavy Metal",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/07/Metallica_at_The_O2_Arena_London_2008.jpg",
//                descriptionEn = "Pioneers of heavy metal music formed in 1981.",
//                descriptionRu = "Пионеры хэви-метала, группа основана в 1981 году.",
//                descriptionBg = "Пионери на хеви метъла, създадени през 1981 г.",
//                concertCities = "San Francisco, Moscow, Madrid"
//            ),
//            ArtistNetworkModel(
//                name = "AC/DC", genre = "Hard Rock",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c2/ACDC_Black_Ice_Tour.jpg",
//                descriptionEn = "Iconic Australian rock band known for high-energy performances.",
//                descriptionRu = "Культовая австралийская рок-группа, известная своей бешеной энергетикой.",
//                descriptionBg = "Иконична австралийска рок група, известна с енергичните си изпълнения.",
//                concertCities = "Sydney, Munich, Vienna"
//            ),
//            ArtistNetworkModel(
//                name = "Motionless in White", genre = "Metalcore",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/3/30/Motionless_In_White_2018.jpg",
//                descriptionEn = "American metalcore band from Scranton, Pennsylvania.",
//                descriptionRu = "Американская металкор-группа из Скрэнтона, Пенсильвания.",
//                descriptionBg = "Американска метълкор група от Скрантън, Пенсилвания.",
//                concertCities = "Chicago, Toronto, Las Vegas"
//            ),
//            ArtistNetworkModel(
//                name = "Empty Skyline", genre = "Alternative Metal",
//                imageUrl = "https://cdn-images.dzcdn.net/images/cover/269b91a5da7bcbcd6dbfe2f53f8a8f1d/0x1900-000000-80-0-0.jpg",
//                descriptionEn = "Rising stars in the alternative metal scene.",
//                descriptionRu = "Восходящие звезды на сцене альтернативного метала.",
//                descriptionBg = "Изгряващи звезди на алтернативната метъл сцена.",
//                concertCities = "Plovdiv, Varna, Sofia"
//            ),
//            ArtistNetworkModel(
//                name = "Disturbed", genre = "Heavy Metal",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/11/Disturbed_2016.jpg",
//                descriptionEn = "Hard-hitting metal band famous for 'Down with the Sickness'.",
//                descriptionRu = "Мощная метал-группа, известная хитом 'Down with the Sickness'.",
//                descriptionBg = "Мощна метъл група, известна с хита 'Down with the Sickness'.",
//                concertCities = "Chicago, Dallas, Amsterdam"
//            ),
//            ArtistNetworkModel(
//                name = "MORGENSHTERN", genre = "Hip-Hop",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/6/6f/Morgenshtern.jpg",
//                descriptionEn = "Provocative rap artist and showman.",
//                descriptionRu = "Провокационный рэп-исполнитель и шоумен.",
//                descriptionBg = "Провокативен рап изпълнител и шоумен.",
//                concertCities = "Dubai, Limassol, Belgrade"
//            ),
//            ArtistNetworkModel(
//                name = "FACE", genre = "Hip-Hop",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/2/23/Face_rapper_2018.jpg",
//                descriptionEn = "Popular youth rapper known for explosive bangers.",
//                descriptionRu = "Популярный молодежный рэпер, автор взрывных бэнгеров.",
//                descriptionBg = "Популярен младежки рапър, известен с експлозивни хитове.",
//                concertCities = "Riga, Warsaw, Prague"
//            ),
//            ArtistNetworkModel(
//                name = "Three Days Grace", genre = "Rock",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/5/52/Three_Days_Grace_2018.jpg",
//                descriptionEn = "Canadian rock band with powerful emotional lyrics.",
//                descriptionRu = "Канадская рок-группа с сильными эмоциональными текстами.",
//                descriptionBg = "Канадска рок група със силни емоционални текстове.",
//                concertCities = "Toronto, Vancouver, London"
//            ),
//            ArtistNetworkModel(
//                name = "Skillet", genre = "Christian Rock",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/5/5b/Skillet_-_2019319183424_2019-11-15_Columbiahalle_-_Sven_-_1D_X_MK_II_-_0322_-_B3P7497.jpg",
//                descriptionEn = "Hard rock band with an inspiring symphonic sound.",
//                descriptionRu = "Хард-рок группа с вдохновляющим симфоническим звучанием.",
//                descriptionBg = "Хард рок група с вдъхновяващо симфонично звучене.",
//                concertCities = "Memphis, Atlanta, Warsaw"
//            ),
//            ArtistNetworkModel(
//                name = "Papa Roach", genre = "Nu Metal",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/4e/Papa_Roach_2017.jpg",
//                descriptionEn = "Creators of the legendary anthem 'Last Resort'.",
//                descriptionRu = "Создатели легендарного гимна 'Last Resort'.",
//                descriptionBg = "Създатели на легендарния химн 'Last Resort'.",
//                concertCities = "Sacramento, Paris, Rome"
//            ),
//            ArtistNetworkModel(
//                name = "My Darkest Days", genre = "Post-Grunge",
//                imageUrl = "https://picsum.photos/seed/MyDarkestDays/600/400", // Заглушка, так как их официальных фото мало
//                descriptionEn = "Canadian rock band famous for their hit 'Porn Star Dancing'.",
//                descriptionRu = "Канадская рок-группа, прославившаяся хитом 'Porn Star Dancing'.",
//                descriptionBg = "Канадска рок група, известна с хита си 'Porn Star Dancing'.",
//                concertCities = "Ontario, Montreal"
//            ),
//            ArtistNetworkModel(
//                name = "Ellie Goulding", genre = "Pop",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/14/Ellie_Goulding_2014.jpg",
//                descriptionEn = "British singer with a unique vocal style.",
//                descriptionRu = "Британская певица с уникальным вокальным стилем.",
//                descriptionBg = "Британска певица с уникален вокален стил.",
//                concertCities = "London, Manchester, Sydney"
//            ),
//            ArtistNetworkModel(
//                name = "Александр Ревва (Артур Пирожков)", genre = "Pop",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/d/df/Alexander_Revva_2019.jpg",
//                descriptionEn = "Famous comedian, actor and pop singer.",
//                descriptionRu = "Известный комик, актер и поп-исполнитель.",
//                descriptionBg = "Известен комик, актьор и поп певец.",
//                concertCities = "Moscow, Minsk, Almaty"
//            ),
//            ArtistNetworkModel(
//                name = "Rammstein", genre = "Industrial Metal",
//                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f3/Rammstein_-_2017204225026_2017-07-23_Nyon_-_Live_Music_Photography_by_Ralf_Ruckebusch.jpg",
//                descriptionEn = "German metal band known for incredible fire shows.",
//                descriptionRu = "Немецкая метал-группа, знаменитая невероятными огненными шоу.",
//                descriptionBg = "Немска метъл група, известна с невероятните си огнени шоута.",
//                concertCities = "Berlin, Munich, Paris"
//            )
//        )
//
//        for (artist in artistsToUpload) {
//            val newDoc = collection.document()
//            val artistWithId = artist.copy(id = newDoc.id)
//            newDoc.set(artistWithId).await()
//        }
//
//        Log.d("SEEDER", "УСПЕХ! ${artistsToUpload.size} артистов успешно добавлены в Firebase!")
//    }
//}