package com.example.movierecom_rsl

// Model Data Class
public class MovieModelClass(name: String, id: String, image: String, genre: ArrayList<String>) {
    var id: String
    var name: String
    var image: String
    var genre: ArrayList<String>
    var hashMap: HashMap<String, Int>

    init {
        this.id = id
        this.name = name
        this.image = image
        this.genre = genre
        hashMap = HashMap<String, Int>()
        for (x in genre) {
            hashMap.put(x, 1)
        }
    }

    @JvmName("getId1")
    fun getId(): String {
        return id
    }

    @JvmName("setId1")
    fun setId(id: String) {
        this.id = id
    }

    @JvmName("getName1")
    fun getName(): String {
        return this.name
    }

    @JvmName("setName1")
    fun setName(name: String) {
        this.name = name
    }

    @JvmName("getImage1")
    fun getImage(): String {
        return this.image
    }

    @JvmName("setImage1")
    fun setImage(image: String) {
        this.image = image
    }

    @JvmName("getGenre1")
    fun getGenre(): ArrayList<String> {
        return this.genre
    }

    @JvmName("setGenre1")
    fun setGenre(genre: ArrayList<String>) {
        this.genre = genre
    }

    fun isRecommended(genereTocheck: ArrayList<String>): Boolean {
        for (x in genereTocheck) {
            if (hashMap.containsKey(x)) {
                return true
            }
        }
        return false
    }


}
