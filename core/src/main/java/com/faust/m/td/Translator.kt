package com.faust.m.td

/**
 * A Translator instance may be used to translate a sentence into another language. It is not a real
 * translator, as I don't know how I would create one.
 * Note on the property toLanguage: I could have used an enum with only one value (=FRENCH) to
 * have a cleaner code, but since it's a dummy project anyway, I preferred to show the use of
 * Exceptions.
 *
 * @property toLanguage sentences will be translated into this language
 * @constructor creates a default Translator set to translate in French
 */
class Translator(toLanguage: String = "French") {

    var toLanguage:String = toLanguage
        set(value) {
            if ("French" != value) throw TranslatorException("We are unable to translate to \"$toLanguage\" for now. More languages will be supported in the future.")
            field = value
        }

    /**
     * Translate a sentence into the pre-defined language.
     *
     * @throws TranslatorException if toLanguage is not recognized  or if the sentence is not
     * translatable
     * @return the translated sentence
     */
    fun translate(sentence: CharSequence): String {
        if ("French" != toLanguage) throw TranslatorException("Unable to translate into:\"$toLanguage\"")
        return when(sentence) {
            "Hello" -> "Bonjour"
            "I don't speak French" -> "Je ne parle pas la France"
            "My umbrella is in the kitchen" -> "Mon parapluie est dans la cuisine"
            "Can I have a coffee? Or two?" -> "Et java, il fait le café"
            "Kotlin is great!" -> "Kotlin est super!"
            "Where is nature?" -> "La nature, c'est par où?"
            "What is nature?" -> "Mais qu'est ce que la nature?"
            "It's too hot!" -> "Il fait trop chaud!"
            "I am cold" -> "J'ai froid"
            "Where can I eat?" -> "C'est par où pour manger?"
            "Do you have cakes?" -> "Vous avez du gâteau?"
            "The cake is a lie" -> "C'est du gâteau"
            "Can I read the latest Garfield?" -> "Je peux avoir le dernier Garfield?"
            "I am looking for a job" -> "Je cherche un travail"
            else -> throw TranslatorException("Cannot translate:\"$sentence\"")
        }
    }
}

class TranslatorException(message: String) : Exception(message)
