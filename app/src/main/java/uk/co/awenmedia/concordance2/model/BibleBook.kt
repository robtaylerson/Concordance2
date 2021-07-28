package uk.co.awenmedia.concordance2.model

class BibleBook(val book : String, val chapters : List<Chapter>) {

            class Chapter(val chapter : String, val verses : List<Verse>) {}
            class Verse (val verse : String, val text : String) {}


}