package myplayground.example.jakpost.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.jakpost.model.News

class FakeNewsRepository : NewsRepository {
    private val newsList = getFakeNewsList().toMutableList()

    override fun fetchAll(search: String?): Flow<List<News>> {
        return flow {
            delay(1500)

            emit(newsList.filter {
                search == null || search == ""
                        || it.title.contains(search, ignoreCase = true)
            })
        }
    }

    companion object {
        @Volatile
        private var instance: FakeNewsRepository? = null

        fun getInstance(): FakeNewsRepository = instance ?: synchronized(this) {
            FakeNewsRepository().apply {
                instance = this
            }
        }
    }
}

private fun getFakeNewsList(): List<News> = listOf(
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/09/12/govt-to-no-longer-use-isa-al-masih-for-christian-holidays",
        title = "Govt to no longer use ‘Isa Al-Masih’ for Christian holidays",
        image = "https://img.jakpost.net/c/2021/04/02/2021_04_02_111974_1617360657._thumbnail.jpg",
        headline = "The government will no longer use the term Isa Al-Masih when referring to Christian holidays, ending decades of state policy to refer to Jesus Christ, a key figure in the Christian faith that is professed by millions of Indonesians, by his Islamic name.",
        category = "Society",
        publishedAt = "17 hours ago",
        premiumBadge = "premium"
    ),
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/09/12/anies-muhaimin-hope-to-maintain-pks-backing",
        title = "Anies, Muhaimin hope to maintain PKS backing",
        image = "https://img.jakpost.net/c/2023/09/03/2023_09_03_141973_1693746435._thumbnail.jpg",
        headline = "Presumptive presidential candidate Anies Baswedan and his running mate Muhaimin Iskandar are hopeful that the Prosperous Justice Party (PKS) will continue to back their candidacy.",
        category = "Presidential Race",
        publishedAt = "17 hours ago",
        premiumBadge = "premium"
    ),
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/09/12/why-nu-is-such-a-powerful-force-in-2024-election",
        title = "Why NU is such a powerful force in 2024 election",
        image = "https://img.jakpost.net/c/2020/10/26/2020_10_26_106402_1603698130._thumbnail.jpg",
        headline = "Perhaps there is no mass organization more influential during election years than Nahdlatul Ulama (NU), the traditionalist Islamic organization that analysts say could decide the outcome the tight 2024 presidential election.",
        category = "Presidential Race",
        publishedAt = "17 hours ago",
        premiumBadge = "premium"
    ),
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/09/12/kpk-employee-fired-over-sexual-harassment-bribery",
        title = "KPK employee fired over sexual harassment, bribery",
        image = "https://img.jakpost.net/c/2023/06/29/2023_06_29_139786_1688047631._thumbnail.jpg",
        headline = "The Corruption Eradication Commission (KPK) has fired a detention center employee for taking bribes and sexually harassing the wife of a detainee.",
        category = "Politics",
        publishedAt = "19 hours ago",
        premiumBadge = "premium"
    ),
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/09/12/golkar-execs-deny-rumors-of-ganjar-ridwan-pairing",
        title = "Golkar execs deny rumors of Ganjar-Ridwan pairing",
        image = "https://img.jakpost.net/c/2023/01/29/2023_01_29_134966_1675003671._thumbnail.jpg",
        headline = "Both Golkar's chairman and deputy chair have denied any rumors of pairing Ridwan Kamil with the presumptive PDI-P nominee, an option that the party's advisory council chair hinted at last week.",
        category = "Presidential Race",
        publishedAt = "23 hours ago",
        premiumBadge = "premium"
    ),
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/07/10/jakarta-police-cling-to-staggered-working-hours-plan-despite-skepticism",
        title = "Jakarta Police cling to staggered working hours plan despite skepticism",
        image = "https://img.jakpost.net/c/2023/02/01/2023_02_01_135079_1675230408._thumbnail.jpg",
        headline = "The Jakarta Police have continued to advocate for staggered working hours to alleviate Jakarta's perennial traffic woes, even after failing to convince workers and employers to alter their schedules last year.",
        category = "Jakarta",
        publishedAt = "2 months ago",
        premiumBadge = "premium",
    ),
)

