//
//  MainTabView.swift
//  iosApp
//
//  Created by kenjimaeda on 13/12/25.
//

import SwiftUI

struct MainScreen: View {
    @State private var selectedTab: TabItem = .home
    
    var body: some View {
        TabView(selection: $selectedTab) {
            ForEach(TabItem.allCases, id: \.self) { tabItem in
                getViewForTab(tabItem)
                    .tabItem {
                        Label(tabItem.title, systemImage: tabItem.iconName)
                            .font(FontsApp.bodySmall)
                    }
                    .tag(tabItem)
            }
        }
        .tint(ColorsApp.tertiary)
        
    }
    
    @ViewBuilder
    private func getViewForTab(_ tabItem: TabItem) -> some View {
        switch tabItem {
        case .home:
            HomeScreen()
                .id(tabItem == TabItem.home ? "home" : "")
        case .article:
            ArticleScreen()
                .id(tabItem == TabItem.article ?  "article" : "")
        }
    }
}



