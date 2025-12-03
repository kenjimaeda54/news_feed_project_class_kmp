//
//  HomeScreen.swift
//  iosApp
//
//  Created by kenjimaeda on 27/11/25.
//

import SwiftUI

struct HomeScreen: View {
    @StateObject private var homeStore = HomeStore()
    
    
    var body: some View {
        VStack {
            if(homeStore.state.isLoading) {
                Text("Is loading")
            }
            
            if(homeStore.state.error == nil)  {
                List {
                    ForEach(homeStore.state.currentNews.articles,id: \.url) { article in
                        Text(article.author)
                    }
                }
            }
        }
    }
}

#Preview {
    HomeScreen()
}
