package com.example.practice9

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // 햄버거 버튼으로 메뉴처럼 프래그먼트 선택 하도록
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        // 홈 프래그먼트가 가장 탑 목적지
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        // 클릭 시 업 버튼이 아닌 햄버거 버튼으로 모두 적용되도록
        val topDest = setOf(R.id.home2, R.id.frag1, R.id.frag2)
        appBarConfiguration = AppBarConfiguration(topDest , drawerLayout)

        // 앱바랑 연결
        // 타이틀바 변경되도록
        setupActionBarWithNavController(navController,appBarConfiguration)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setupWithNavController(navController)
    }

    // 업 버튼
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        // 실패 시 기존에 있던
        return navController.navigateUp(appBarConfiguration) || return super.onSupportNavigateUp()
    }

    // 메뉴 보여주기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 인자로 넘어온 menu에
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    // 메뉴 선택시 어떤 행위
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.frag1, R.id.frag2 ->
                item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView))

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}