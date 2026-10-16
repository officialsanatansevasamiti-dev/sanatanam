package com.example.data

import com.example.model.ContentCategory
import com.example.model.DeityInfo
import com.example.model.DevotionalItem
import com.example.model.FestivalItem
import com.example.model.PanchangDay
import com.example.model.SuvicharItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DevotionalRepository {

    private val _favoriteIds = MutableStateFlow<Set<String>>(setOf("hanuman_chalisa", "ganesh_aarti", "shiv_tandav"))
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    fun toggleFavorite(id: String) {
        val current = _favoriteIds.value
        _favoriteIds.value = if (current.contains(id)) current - id else current + id
    }

    fun getTodayPanchang(): PanchangDay {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("hi", "IN"))
        val dateStr = dateFormat.format(Date())
        return PanchangDay(
            gregorianDate = dateStr,
            dayOfWeekHindi = "गुरुवार (Thursday)",
            vikramSamvat = "विक्रम संवत २०८३ (कालयुक्त)",
            shakaSamvat = "शक संवत १९४८",
            monthNameHindi = "कार्तिक मास (Kartik)",
            paksha = "शुक्ल पक्ष (Shukla Paksha)",
            tithi = "एकादशी (Ekadashi) - रात्रि ०१:२४ तक",
            nakshatra = "रोहिणी (Rohini) - दोपहर ०२:४५ तक",
            yog = "वृद्धि योग (Vriddhi Yoga)",
            karan = "वणिज करण (Vanij Karana)",
            sunRise = "०६:२२ AM",
            sunSet = "०५:५४ PM",
            moonRise = "०२:१५ PM",
            rahuKaal = "०१:३० PM - ०३:०० PM (अशुभ)",
            abhijitMuhurat = "११:४४ AM - १२:३० PM (सर्वोत्तम शुभ)",
            amritKaal = "०८:१० AM - ०९:४२ AM",
            vratOrFestival = "प्रबोधिनी एकादशी (देवउठनी एकादशी)",
            choghadiyaDay = "शुभ (०६:२२ - ०७:४८) | लाभ (१२:०८ - ०१:३२)"
        )
    }

    fun getSuvicharList(): List<SuvicharItem> = listOf(
        SuvicharItem(
            id = "gita_2_47",
            sanskritVerse = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥",
            source = "श्रीमद्भगवद्गीता - अध्याय २, श्लोक ४७",
            hindiMeaning = "तुम्हारा अधिकार केवल कर्म करने में है, उसके फलों में कभी नहीं। इसलिए कर्म के फल की इच्छा मत करो और न ही कर्म न करने में तुम्हारी आसक्ति हो।",
            englishMeaning = "You have a right only to perform your prescribed duty, but never to the fruits of action. Never consider yourself the cause of results, nor be attached to inaction.",
            speaker = "भगवान श्रीकृष्ण (Lord Krishna)"
        ),
        SuvicharItem(
            id = "gita_4_7",
            sanskritVerse = "यदा यदा हि धर्मस्य ग्लानिर्भवति भारत।\nअभ्युत्थानमधर्मस्य तदात्मानं सृजाम्यहम्॥",
            source = "श्रीमद्भगवद्गीता - अध्याय ४, श्लोक ७",
            hindiMeaning = "हे भारत! जब-जब धर्म की हानि और अधर्म की वृद्धि होती है, तब-तब मैं धर्म के अभ्युत्थान के लिए स्वयं को प्रकट करता हूँ।",
            englishMeaning = "Whenever there is a decline in righteousness and an increase in unrighteousness, O Bharata, at that time I manifest Myself.",
            speaker = "भगवान श्रीकृष्ण (Lord Krishna)"
        ),
        SuvicharItem(
            id = "ramayana_1",
            sanskritVerse = "धर्मो रक्षति रक्षितः।",
            source = "महाभारत / मनुस्मृति",
            hindiMeaning = "जो धर्म की रक्षा करता है, धर्म उसकी रक्षा करता है। सनातन सत्य ही जीवन की सर्वोच्च शक्ति है।",
            englishMeaning = "Righteousness protects those who protect righteousness. Truth and Dharma are the eternal shields of life.",
            speaker = "महर्षि वेदव्यास (Maharshi Vedvyas)"
        ),
        SuvicharItem(
            id = "upanishad_1",
            sanskritVerse = "असतो मा सद्गमय। तमसो मा ज्योतिर्गमय। मृत्योर्माऽमृतं गमय॥",
            source = "बृहदारण्यक उपनिषद्",
            hindiMeaning = "मुझे असत्य से सत्य की ओर ले चलो। मुझे अंधकार से प्रकाश की ओर ले चलो। मुझे मृत्यु से अमरता की ओर ले चलो।",
            englishMeaning = "Lead me from untruth to truth. Lead me from darkness to divine light. Lead me from death to immortality.",
            speaker = "वैदिक ऋषि (Vedic Sage)"
        )
    )

    fun getDeities(): List<DeityInfo> = listOf(
        DeityInfo(
            id = "shri_ram",
            nameHindi = "मर्यादा पुरुषोत्तम श्रीराम",
            nameEnglish = "Lord Shri Ram",
            titleHindi = "अयोध्या धाम",
            mantra = "श्री राम जय राम जय जय राम",
            primaryColorHex = 0xFFE65100,
            description = "धर्म, मर्यादा, सत्य और करुणा के सर्वोच्च आदर्श भगवान श्रीराम।"
        ),
        DeityInfo(
            id = "mahadev",
            nameHindi = "देवाधिदेव महादेव शिव",
            nameEnglish = "Mahadev Shiva",
            titleHindi = "कैलास वासी / काशी विश्वनाथ",
            mantra = "ॐ नमः शिवाय",
            primaryColorHex = 0xFF1565C0,
            description = "सृष्टि, स्थिति और संहार के अधिपति, भोलेनाथ आशुतोष महादेव।"
        ),
        DeityInfo(
            id = "hanuman_ji",
            nameHindi = "संकटमोचन श्री हनुमान जी",
            nameEnglish = "Shri Hanuman Ji",
            titleHindi = "पवनपुत्र अंजनी सुत",
            mantra = "ॐ हं हनुमते नमः",
            primaryColorHex = 0xFFD84315,
            description = "बल, बुद्धि, विद्या और अतुलित पराक्रम के दाता, रामभक्त हनुमान।"
        ),
        DeityInfo(
            id = "radha_krishna",
            nameHindi = "श्री राधा कृष्ण",
            nameEnglish = "Shri Radha Krishna",
            titleHindi = "वृन्दावन धाम",
            mantra = "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे। हरे राम हरे राम राम राम हरे हरे॥",
            primaryColorHex = 0xFF0288D1,
            description = "प्रेम, भक्ति, दिव्य ज्ञान और आनंद के स्वरूप श्री राधा-माधव।"
        ),
        DeityInfo(
            id = "maa_durga",
            nameHindi = "जगदम्बा माँ दुर्गा",
            nameEnglish = "Maa Durga Bhavani",
            titleHindi = "शक्ति स्वरूपा",
            mantra = "ॐ ऐं ह्रीं क्लीं चामुण्डायै विच्चे",
            primaryColorHex = 0xFFC2185B,
            description = "समस्त दैहिक, दैविक और भौतिक विघ्नों का नाश करने वाली आदि पराशक्ति।"
        ),
        DeityInfo(
            id = "ganesh_ji",
            nameHindi = "विघ्नहर्ता श्री गणेश",
            nameEnglish = "Lord Shri Ganesha",
            titleHindi = "प्रथम पूज्य",
            mantra = "ॐ गं गणपतये नमः",
            primaryColorHex = 0xFFF57C00,
            description = "सर्व कार्य सिद्धि, रिद्धि-सिद्धि के प्रदाता प्रथम पूज्य गणेश जी।"
        ),
        DeityInfo(
            id = "maa_lakshmi",
            nameHindi = "महालक्ष्मी माता",
            nameEnglish = "Maa Lakshmi",
            titleHindi = "वैभव व ऐश्वर्य दात्री",
            mantra = "ॐ श्रीं ह्रीं श्रीं कमले कमलालये प्रसीद प्रसीद",
            primaryColorHex = 0xFFFBC02D,
            description = "सुख, समृद्धि, धर्म और ऐश्वर्य की अधिष्ठात्री देवी।"
        )
    )

    fun getAllDevotionalItems(): List<DevotionalItem> = listOf(
        DevotionalItem(
            id = "hanuman_chalisa",
            titleHindi = "श्री हनुमान चालीसा",
            titleEnglish = "Shri Hanuman Chalisa",
            deity = "श्री हनुमान जी",
            category = ContentCategory.CHALISA,
            summary = "गोस्वामी तुलसीदास जी द्वारा रचित सर्व कष्ट हरण दिव्य स्तुति।",
            durationText = "8:40",
            lyricsHindi = """
॥ दोहा ॥
श्रीगुरु चरन सरोज रज, निज मनु मुकुरु सुधारि।
बरनउँ रघुबर बिमल जसु, जो दायकु फल चारि॥
बुद्धिहीन तनु जानिके, सुमिरौं पवन-कुमार।
बल बुद्धि बिद्या देहु मोहिं, हरहु कलेस बिकार॥

॥ चौपाई ॥
जय हनुमान ज्ञान गुन सागर।
जय कपीस तिहुँ लोक उजागर॥
राम दूत अतुलित बल धामा।
अंजनि-पुत्र पवनसुत नामा॥

महाबीर बिक्रम बजरंगी।
कुमति निवार सुमति के संगी॥
कंचन बरन बिराज सुबेसा।
कानन कुंडल कुंचित केसा॥

हाथ बज्र औ ध्वजा बिराजै।
काँधे मूँज जनेऊ साजै॥
संकर सुवन केसरीनंदन।
तेज प्रताप महा जग बंदन॥

बिद्यावान गुनी अति चातुर।
राम काज करिबे को आतुर॥
प्रभु चरित्र सुनिबे को रसिया।
राम लखन सीता मन बसिया॥

सूक्ष्म रूप धरि सियहिं दिखावा।
बिकट रूप धरि लंक जरावा॥
भीम रूप धरि असुर संहारे।
रामचंद्र के काज संवारे॥

लाय संजीवन लखन जियाये।
श्रीरघुबीर हरषि उर लाये॥
रघुपति कीन्ही बहुत बड़ाई।
तुम मम प्रिय भरतहि सम भाई॥

सहस बदन तुम्हरो जस गावैं।
अस कहि श्रीपति कंठ लगावैं॥
सनकादिक ब्रह्मादि मुनीसा।
नारद सारद सहित अहीसा॥

जम कुबेर दिगपाल जहाँ ते।
कबि कोबिद कहि सके कहाँ ते॥
तुम उपकार सुग्रीवहिं कीन्हा।
राम मिलाय राज पद दीन्हा॥

तुम्हरो मंत्र बिभीषन माना।
लंकेस्वर भए सब जग जाना॥
जुग सहस्र जोजन पर भानू।
लील्यो ताहि मधुर फल जानू॥

प्रभु मुद्रिका मेलि मुख माहीं।
जलधि लाँघि गये अचरज नाहीं॥
दुर्गम काज जगत के जेते।
सुगम अनुग्रह तुम्हरे तेते॥

राम दुआरे तुम रखवारे।
होत न आज्ञा बिनु पैसारे॥
सब सुख लहै तुम्हारी सरना।
तुम रच्छक काहू को डर ना॥

आपन तेज सम्हारो आपै।
तीनों लोक हाँक तें काँपै॥
भूत पिसाच निकट नहिं आवै।
महाबीर जब नाम सुनावै॥

नासै रोग हरै सब पीरा।
जपत निरंतर हनुमत बीरा॥
संकट तें हनुमान छुड़ावै।
मन क्रम बचन ध्यान जो लावै॥

सब पर राम तपस्वी राजा।
तिन के काज सकल तुम साजा॥
और मनोरथ जो कोई लावै।
सोइ अमित जीवन फल पावै॥

चारों जुग परताप तुम्हारा।
है परसिद्ध जगत उजियारा॥
साधु संत के तुम रखवारे।
असुर निकंदन राम दुलारे॥

अष्ट सिद्धि नौ निधि के दाता।
अस बर दीन जानकी माता॥
राम रसायन तुम्हरे पासा।
सदा रहो रघुपति के दासा॥

तुम्हरे भजन राम को पावै।
जनम जनम के दुख बिसरावै॥
अंत काल रघुबर पुर जाई।
जहाँ जन्म हरि-भक्त कहाई॥

और देवता चित्त न धरई।
हनुमत सेइ सर्ब सुख करई॥
संकट कटै मिटै सब पीरा।
जो सुमिरै हनुमत बलबीरा॥

जै जै जै हनुमान गोसाईं।
कृपा करहु गुरुदेव की नाईं॥
जो सत बार पाठ कर कोई।
छूटहि बंदि महा सुख होई॥

जो यह पढ़ै हनुमान चालीसा।
होय सिद्धि साखी गौरीसा॥
तुलसीदास सदा हरि चेरा।
कीजै नाथ हृदय महँ डेरा॥

॥ दोहा ॥
पवनतनय संकट हरन, मंगल मूरति रूप।
राम लखन सीता सहित, हृदय बसहु सुर भूप॥
            """.trimIndent(),
            lyricsEnglish = "Shri Guru Charana Saroja Raja, Nija Manu Mukuru Sudhari... Jai Hanuman Gyan Guna Sagara, Jai Kapisa Tihun Loka Ujagara...",
            meaningHindi = "श्री गुरु के चरण कमलों की धूलि से अपने मन रूपी दर्पण को पवित्र करके, मैं श्री रघुवीर के निर्मल यश का वर्णन करता हूँ, जो चारों फलों (धर्म, अर्थ, काम, मोक्ष) को देने वाला है।"
        ),
        DevotionalItem(
            id = "ganesh_aarti",
            titleHindi = "श्री गणेश आरती",
            titleEnglish = "Shri Ganesh Aarti (Jai Ganesh Deva)",
            deity = "श्री गणेश जी",
            category = ContentCategory.AARTI,
            summary = "प्रथम पूज्य विघ्नहर्ता भगवान गणेश जी की पावन आरती।",
            durationText = "4:15",
            lyricsHindi = """
जय गणेश जय गणेश, जय गणेश देवा।
माता जाकी पार्वती, पिता महादेवा॥

एक दंत दयावंत, चार भुजा धारी।
माथे सिंदूर सोहे, मूसे की सवारी॥
पान चढ़े फल चढ़े, और चढ़े मेवा।
लड्डुअन का भोग लगे, संत करें सेवा॥
जय गणेश जय गणेश, जय गणेश देवा...

अंधेन को आँख देत, कोढ़िन को काया।
बांझन को पुत्र देत, निर्धन को माया॥
‘सूर’ श्याम शरण आए, सफल कीजे सेवा।
माता जाकी पार्वती, पिता महादेवा॥
जय गणेश जय गणेश, जय गणेश देवा...
            """.trimIndent(),
            lyricsEnglish = "Jai Ganesh Jai Ganesh Jai Ganesh Deva, Mata Jaki Parvati Pita Mahadeva...",
            meaningHindi = "भगवान गणेश जी की जय हो, जिनकी माता पार्वती और पिता देवाधिदेव महादेव हैं। वे सभी भक्तों के संकटों का निवारण करते हैं।"
        ),
        DevotionalItem(
            id = "shiv_tandav",
            titleHindi = "श्री शिव ताण्डव स्तोत्रम्",
            titleEnglish = "Shri Shiva Tandava Stotram",
            deity = "महादेव शिव",
            category = ContentCategory.STOTRA,
            summary = "रावण विरचित भगवान शिव के रौद्र व मंगलमय ताण्डव का महा स्तोत्र।",
            durationText = "9:15",
            lyricsHindi = """
जटाटवीगलज्जलप्रवाहपावितस्थले
गलेऽवलम्ब्य लम्बितां भुजङ्गतुङ्गमालिकाम्।
डमड्डमड्डमड्डमन्निनादवड्डमर्वयं
चकार चण्डताण्डवं तनोतु नः शिवः शिवम्॥१॥

जटाकटाहसम्भ्रमभ्रमन्निलिम्पनिर्झरी-
विलोलवीचिवल्लरीविराजमानमूर्धनि।
धगद्धगद्धगज्ज्वलल्ललाटपट्टपावके
किशोरचन्द्रशेखरे रतिः प्रतिक्षणं मम॥२॥

धराधरेन्द्रनन्दिनीविलासबन्धुबन्धुर-
स्फुरद्दिगन्तसन्ततिप्रमोदमानमानसे।
कृपाकटाक्षधोरणीनिरुद्धदुर्धरापदि
क्वचिद्दिगम्बरे मनो विनोदमेतु वस्तुनि॥३॥

जटाभुजङ्गपिङ्गलस्फुरत्फणामणिप्रभा-
कदम्बकुङ्कुमद्रवप्रलिप्तदिग्वadhūमुखे।
मदान्धसिन्धुरस्फुरत्त्वगुत्तरीयमेदुरे
मनो विनोदमद्भुतं बिभर्तु भूतभर्तरि॥४॥
            """.trimIndent(),
            lyricsEnglish = "Jatatavee Gala Jjala Pravaha Pavitasthale, Galeavalambya Lambitam Bhujangatungamalikam...",
            meaningHindi = "जिनके सघन जटाजूट रूपी वन से निकलती गंगा जी की पवित्र धारा से उनका कंठ पावन है, जिनके गले में सर्पों का विशाल हार सुशोभित है, डमरू की डम-डम ध्वनि के साथ जो प्रचंड तांडव करते हैं, वे भगवान शिव हमारा मंगल करें।"
        ),
        DevotionalItem(
            id = "mahamrityunjaya",
            titleHindi = "महामृत्युंजय मंत्र",
            titleEnglish = "Mahamrityunjaya Mantra",
            deity = "महादेव शिव",
            category = ContentCategory.MANTRA,
            summary = "ऋग्वेद का मोक्ष, स्वास्थ्य व अकाल मृत्यु निवारक दिव्य संजीवनी मंत्र।",
            durationText = "3:30",
            lyricsHindi = """
ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।
उर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय मामृतात्॥

॥ मंत्र का भावार्थ ॥
हम त्रिनेत्रधारी भगवान शिव की आराधना करते हैं, जो समस्त लोकों को सुगंधित और पुष्ट करने वाले हैं। जिस प्रकार पका हुआ खरबूजा अपनी बेल के बंधन से सहज ही मुक्त हो जाता है, उसी प्रकार हम मृत्यु के भय और संसार के बंधनों से मुक्त होकर अमरता (मोक्ष) को प्राप्त हों।
            """.trimIndent(),
            lyricsEnglish = "Om Tryambakam Yajamahe Sugandhim Pushti Vardhanam, Urvarukamiva Bandhanan Mrityor Mukshiya Mamritat.",
            meaningHindi = "यह मंत्र अकाल मृत्यु के भय को मिटाता है और मानसिक शांति एवं दीर्घायु प्रदान करता है।"
        ),
        DevotionalItem(
            id = "gayatri_mantra",
            titleHindi = "गायत्री महामंत्र",
            titleEnglish = "Gayatri Mahamantra",
            deity = "माँ गायत्री / सूर्य देव",
            category = ContentCategory.MANTRA,
            summary = "वेदों का परम सार, सद्बुद्धि और ज्ञान का प्रकाशपुंज महामंत्र।",
            durationText = "3:10",
            lyricsHindi = """
ॐ भूर्भुवः स्वः तत्सवितुर्वरेण्यं।
भर्गो देवस्य धीमहि धियो यो नः प्रचोदयात्॥

॥ भावार्थ ॥
उस प्राणस्वरूप, दुःखनाशक, सुखस्वरूप, श्रेष्ठ, तेजस्वी, पापनाशक, देवस्वरूप परमात्मा को हम अपनी अन्तरात्मा में धारण करें। वह परमात्मा हमारी बुद्धि को सन्मार्ग की ओर प्रेरित करे।
            """.trimIndent(),
            lyricsEnglish = "Om Bhur Bhuvah Swah Tat Savitur Varenyam, Bhargo Devasya Dheemahi Dhiyo Yo Nah Prachodayat.",
            meaningHindi = "समस्त वेदों की जननी गायत्री माता का यह मंत्र आत्मिक जागृति का सर्वोच्च साधन है।"
        ),
        DevotionalItem(
            id = "ram_stuti",
            titleHindi = "श्री रामचन्द्र कृपालु भजु मन",
            titleEnglish = "Shri Ramachandra Kripalu Bhaju Man",
            deity = "श्री राम",
            category = ContentCategory.AARTI,
            summary = "गोस्वामी तुलसीदास जी द्वारा रचित अनुपम श्रीराम स्तुति।",
            durationText = "5:45",
            lyricsHindi = """
श्रीरामचन्द्र कृपालु भजु मन हरण भवभय दारुणम्।
नवकंज लोचन, कंज मुख, कर कंज, पद कंजारुणम्॥

कंदर्प अगणित अमित छबि, नवनील नीरद सुन्दरम्।
पट पीत मानहु तड़ित रुचि शुचि नौमि जनक सुतावरम्॥

भजु दीनबन्धु दिनेश दानव दैत्यवंश निकन्दनम्।
रघुनन्द आनन्दकन्द कोशलचन्द दशरथ नन्दनम्॥

सिर मुकुट कुंडल तिलक चारु उदारु अंग विभूषणम्।
आजानुभुज शर चाप धर, संग्राम-जित-खरदूषणम्॥

इति वदति तुलसीदास शंकर-शेष-मुनि-मन-रंजनम्।
मम हृदय-कंज-निवास कुरु, कामादि खल-दल-गंजनम्॥
            """.trimIndent(),
            lyricsEnglish = "Shri Ramachandra Kripalu Bhaju Mana Harana Bhavabhaya Darunam...",
            meaningHindi = "हे मन! कृपालु श्रीरामचन्द्रजी का भजन कर, जो संसार के दारुण भय को दूर करने वाले हैं। जिनके नेत्र, मुख और चरण नवीन कमल के समान हैं।"
        ),
        DevotionalItem(
            id = "aarti_kunj_bihari",
            titleHindi = "आरती कुञ्जबिहारी की",
            titleEnglish = "Aarti Kunj Bihari Ki",
            deity = "श्री राधा कृष्ण",
            category = ContentCategory.AARTI,
            summary = "भगवान श्री बांके बिहारी कृष्ण कन्हैया की मधुर आरती।",
            durationText = "5:10",
            lyricsHindi = """
आरती कुञ्जबिहारी की, श्री गिरिधर कृष्णमुरारी की॥
गले में बैजन्ती माला, बजावै मुरली मधुर बाला।
श्रवण में कुण्डल झलकाला, नंद के आनंद नंदलाला।
श्री गिरिधर कृष्णमुरारी की॥

कनकमय मुकुट बिलस रह्यो, लकुटि कर कामरि सोहै।
नयन में बसि गये मनमोहन, रूप लखि कोटि मदन मोहै।
श्री गिरिधर कृष्णमुरारी की॥
            """.trimIndent(),
            lyricsEnglish = "Aarti Kunj Bihari Ki, Shri Giridhar Krishna Murari Ki...",
            meaningHindi = "गले में वैजयंती माला और हाथों में मनोहर बांसुरी धारण करने वाले श्री कृष्ण मुरारी की पावन आरती।"
        ),
        DevotionalItem(
            id = "durga_chalisa",
            titleHindi = "श्री दुर्गा चालीसा",
            titleEnglish = "Shri Durga Chalisa",
            deity = "माँ दुर्गा",
            category = ContentCategory.CHALISA,
            summary = "आदिशक्ति माँ जगदम्बा की कृपा प्राप्त करने हेतु ४० पावन चौपाइयां।",
            durationText = "7:50",
            lyricsHindi = """
॥ नमो नमो दुर्गे सुख करनी, नमो नमो अम्बे दुःख हरनी ॥
निरंकार है ज्योति तुम्हारी, तिहूँ लोक फैली उजियारी।
शशि ललाट मुख महाविशाला, नेत्र लाल भृकुटि विकराला।
रूप मातु को अधिक सुहावे, दरश करत जन अति सुख पावे।
तुम संसार शक्ति लय कीना, पालन हेतु अन्न धन दीना।
अन्नपूर्णा हुई जग पाला, तुम ही आदि सुन्दरी बाला।
            """.trimIndent(),
            lyricsEnglish = "Namo Namo Durge Sukha Karani, Namo Namo Ambe Dukh Harani...",
            meaningHindi = "हे माँ दुर्गे! आपको बारंबार नमस्कार है, जो सुखों को देने वाली और समस्त दुःखों का हरण करने वाली हैं।"
        )
    )

    fun getUpcomingFestivals(): List<FestivalItem> = listOf(
        FestivalItem(
            id = "devuthani_ekadashi",
            titleHindi = "देवउठनी प्रबोधिनी एकादशी व तुलसी विवाह",
            titleEnglish = "Devutthana Ekadashi & Tulsi Vivah",
            dateDisplay = "कार्तिक शुक्ल एकादशी",
            monthName = "कार्तिक (Kartik)",
            dayName = "शुक्रवार",
            deity = "भगवान शालिग्राम व माता तुलसी",
            significance = "भगवान श्री हरि विष्णु चार माह के योगनिद्रा से जागते हैं और चातुर्मास समाप्त होता है।",
            vidhi = "व्रत रखें, तुलसी जी और शालिग्राम जी का विवाह संपन्न कराएं, गन्ने और आंवले का भोग लगाएं।"
        ),
        FestivalItem(
            id = "kartik_purnima",
            titleHindi = "देव दीपावली व कार्तिक पूर्णिमा",
            titleEnglish = "Dev Deepawali & Kartik Purnima",
            dateDisplay = "कार्तिक पूर्णिमा",
            monthName = "कार्तिक (Kartik)",
            dayName = "सोमवार",
            deity = "भगवान शिव व गंगा मैया",
            significance = "देवता काशी में आकर दीपदान करते हैं। त्रिपुरारी शिवजी ने त्रिपुरासुर का वध किया था।",
            vidhi = "पवित्र नदियों में स्नान, सायंकाल देवालयों व सरोवर तट पर दीपदान करें।"
        ),
        FestivalItem(
            id = "maha_shivratri",
            titleHindi = "महाशिवरात्रि महापर्व",
            titleEnglish = "Maha Shivratri",
            dateDisplay = "फाल्गुन कृष्ण चतुर्दशी",
            monthName = "फाल्गुन (Phalguna)",
            dayName = "रविवार",
            deity = "देवाधिदेव महादेव शिव",
            significance = "भगवान शिव और माता पार्वती का पावन विवाह तथा ज्योतिर्लिंग प्राकट्य दिवस।",
            vidhi = "चार प्रहर की पूजा, शिवलिंग पर जलाभिषेक, रुद्राभिषेक, बेलपत्र, धतूरा अर्पण और रात्रि जागरण।"
        ),
        FestivalItem(
            id = "ram_navami",
            titleHindi = "श्री राम नवमी",
            titleEnglish = "Shri Ram Navami",
            dateDisplay = "चैत्र शुक्ल नवमी",
            monthName = "चैत्र (Chaitra)",
            dayName = "बुधवार",
            deity = "मर्यादा पुरुषोत्तम श्रीराम",
            significance = "अयोध्या में भगवान विष्णु के सातवें अवतार प्रभु श्रीराम का पावन प्राकट्य उत्सव।",
            vidhi = "रामायण / सुंदरकांड का पाठ, दोपहर १२ बजे जन्मोत्सव की आरती, भजन-कीर्तन और प्रसाद वितरण।"
        ),
        FestivalItem(
            id = "hanuman_jayanti",
            titleHindi = "श्री हनुमान जन्मोत्सव",
            titleEnglish = "Shri Hanuman Janmotsav",
            dateDisplay = "चैत्र पूर्णिमा",
            monthName = "चैत्र (Chaitra)",
            dayName = "गुरुवार",
            deity = "पवनपुत्र हनुमान",
            significance = "रुद्रावतार अंजनीपुत्र महावीर हनुमान जी का पावन प्राकट्य दिवस।",
            vidhi = "हनुमान चालीसा व सुंदरकांड का ११ पाठ, सिंदूर व चमेली के तेल का चोला, बूंदी के लड्डू का भोग।"
        )
    )

    fun getSanatanSevaSamitiInfo() = SamitiDetails(
        organizationNameHindi = "सनातन सेवा समिति",
        organizationNameEnglish = "Sanatan Seva Samiti",
        officialWebsite = "https://sanatansevasamiti.shrisanatanam.com",
        displayWebsite = "sanatansevasamiti.shrisanatanam.com",
        officialEmail = "officialsanatansevasamiti@gmail.com",
        tagline = "धर्मो रक्षति रक्षितः | सेवा परमो धर्मः",
        mission = "सनातन धर्म के वैदिक संस्कारों, गौ सेवा, अन्नदान, तीर्थ संरक्षण, और जन-कल्याण हेतु समर्पित संस्था।",
        activities = listOf(
            "दैनिक वेद वाणी एवं पंचांग प्रसार",
            "निःशुल्क अन्नक्षेत्र व दरिद्र नारायण सेवा",
            "गौशाला संवर्धन एवं बेसहारा गौवंश की सेवा",
            "प्राचीन मन्दिरों एवं सनातन तीर्थों का जीर्णोद्धार",
            "धार्मिक साहित्य, गीता व चालीसा का निःशुल्क वितरण"
        ),
        developerName = "DG Developers",
        companyName = "Deep Group",
        version = "v1.0.0 (Sanatan Release)"
    )
}

data class SamitiDetails(
    val organizationNameHindi: String,
    val organizationNameEnglish: String,
    val officialWebsite: String,
    val displayWebsite: String,
    val officialEmail: String,
    val tagline: String,
    val mission: String,
    val activities: List<String>,
    val developerName: String,
    val companyName: String,
    val version: String
)
