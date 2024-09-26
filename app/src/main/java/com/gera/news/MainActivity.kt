package com.gera.news

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val items = listOf(
        NewsItems("Philippines Reports Strong Economic Growth",
            "The Philippine economy has shown resilience, with a significant increase in GDP despite global challenges, such as fluctuating commodity prices and geopolitical tensions, which have affected various sectors worldwide. This remarkable growth reflects the adaptability of local businesses and the government's proactive measures to stimulate economic activities, ensuring that the nation remains on a steady path toward recovery and development.\n" +
                    "\n",
            R.drawable.img1),
        NewsItems("Typhoon Season Preparations Underway",
            "Government agencies are ramping up efforts to prepare for the upcoming typhoon season, focusing on disaster preparedness through enhanced training programs, community outreach initiatives, and the allocation of resources for emergency response teams. By collaborating with local organizations and conducting simulations, these agencies aim to minimize the impact of natural disasters, safeguard lives, and ensure that communities are well-equipped to handle the challenges that arise during severe weather events.",
            R.drawable.img2),
        NewsItems("New Education Policy Aims to Enhance Learning",
            "The Department of Education is implementing a new policy to improve access to quality education in rural areas, aiming to bridge the educational gap that exists between urban and rural communities. This initiative includes the establishment of more schools, the provision of necessary teaching materials, and the training of educators to enhance their skills. By prioritizing equitable education opportunities, the department seeks to empower students in these regions and enable them to achieve their full potential, ultimately contributing to the overall growth and development of the country.",
            R.drawable.img3)
    )



    private lateinit var listView: ListView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        listView.adapter = MyListAdapter(items) { item ->
            val fragment = ItemDetailFragment.newInstance(item)

            if (isPortraitMode()) {
                listView.visibility = View.GONE
                fragmentContainer.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        if (isPortraitMode()) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                listView.visibility = View.VISIBLE
                fragmentContainer.visibility = View.GONE
            } else {
                super.onBackPressed()
            }
        } else {

            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun isPortraitMode(): Boolean {
        return resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    }
}