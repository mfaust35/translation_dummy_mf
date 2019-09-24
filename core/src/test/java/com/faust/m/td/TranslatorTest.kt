package com.faust.m.td

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TranslatorTest {

    private val wrongTranslation = "wrongTranslation"
    private var mTranslator = Translator()

    @Test
    fun `translate Hello`() {
        assertEquals("Bonjour",
            mTranslator.translate("Hello"),
            wrongTranslation)
    }

    @Test
    fun `translate I Don't Speak French`() {
        assertEquals("Je ne parle pas la France",
            mTranslator.translate("I don't speak French"),
            wrongTranslation)
    }

    @Test
    fun `translate My umbrella is in the kitchen`() {
        assertEquals("Mon parapluie est dans la cuisine",
            mTranslator.translate("My umbrella is in the kitchen"),
            wrongTranslation)
    }

    @Test
    fun `translate Can I have a coffee`() {
        assertEquals("Et java, il fait le café",
            mTranslator.translate("Can I have a coffee? Or two?"),
            wrongTranslation)
    }

    @Test
    fun `translate Kotlin is great`() {
        assertEquals("Kotlin est super!",
            mTranslator.translate("Kotlin is great!"),
            wrongTranslation)
    }

    @Test
    fun `translate Where is nature`() {
        assertEquals("La nature, c'est par où?",
            mTranslator.translate("Where is nature?"),
            wrongTranslation)
    }

    @Test
    fun `translate What is nature`() {
        assertEquals("Mais qu'est ce que la nature?",
            mTranslator.translate("What is nature?"),
            wrongTranslation)
    }

    @Test
    fun `translate It's too hot`() {
        assertEquals("Il fait trop chaud!",
            mTranslator.translate("It's too hot!"),
            wrongTranslation)
    }

    @Test
    fun `translate I am cold`() {
        assertEquals("J'ai froid",
            mTranslator.translate("I am cold"),
            wrongTranslation)
    }

    @Test
    fun `translate Where can I eat?`() {
        assertEquals("C'est par où pour manger?",
            mTranslator.translate("Where can I eat?"),
            wrongTranslation)
    }

    @Test
    fun `translate Do you have cakes?`() {
        assertEquals("Vous avez du gâteau?",
            mTranslator.translate("Do you have cakes?"),
            wrongTranslation)
    }

    @Test
    fun `translate The cake is a lie`() {
        assertEquals("C'est du gâteau",
            mTranslator.translate("The cake is a lie"),
            wrongTranslation)
    }

    @Test
    fun `translate Can I read the latest Garfield?`() {
        assertEquals("Je peux avoir le dernier Garfield?",
            mTranslator.translate("Can I read the latest Garfield?"),
            wrongTranslation)
    }

    @Test
    fun `translate I am looking for a job`() {
        assertEquals("Je cherche un travail",
            mTranslator.translate("I am looking for a job"),
            wrongTranslation)
    }

    @Test
    fun translateUnknownSentenceShouldThrowException() {
        assertFailsWith(TranslatorException::class,
            "Translator should not translate unknown sentences"
        ) { mTranslator.translate("x") }
    }

    @Test
    fun translateToUnknownLanguageShouldThrowException() {
        mTranslator = Translator("Japanese")
        assertFailsWith(TranslatorException::class,
            "Translator should not translate to unknown language"
        ) { mTranslator.translate("Hello") }
    }

    @Test
    fun setToUnknownLanguageShouldThrowException() {
        assertFailsWith(TranslatorException::class,
            "Translator should not set another language"
        ) { mTranslator.toLanguage = "Japanese" }
    }
}
