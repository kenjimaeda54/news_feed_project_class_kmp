//
//  HomeScreen.swift
//  iosApp
//
//  Created by kenjimaeda on 13/12/25.
//

import SwiftUI
import Shared

struct HomeScreen: View {
    @StateObject private var homeStore = StateFactory.makeHomeStore()
    @State var isShowToastError = false

    var body: some View {
        NavigationStack  {
            if(homeStore.state.isLoading){
                LoadingComponent()
            }else if(homeStore.state.error != nil && !homeStore.state.showToastErrorIfNotConnectionInternet) {
                ErrorComponent()
            }else {
                List {
                    ForEach(
                        homeStore.state.topHeadlines.content,
                        id: \.url
                    ) { content in
                        CardComponent(content: content)
                            .listRowInsets(EdgeInsets())
                            .listRowSeparator(.hidden)
                    }
                }
                .scrollIndicators(.hidden)
                .listStyle(.plain)
            }
        }
        .onAppear {
            homeStore.sendIntent(HomeIntent.LoadTopHeadlines())
        }
        .alert(
            "Sem internet",
            isPresented: $isShowToastError,
            actions: {
                Button("Tentar novamente") {
                    homeStore.sendIntent(
                        HomeIntent.LoadTopHeadlines()
                    )
                }
                if #available(iOS 26.0, *) {
                    Button("Continuar sem atualizar", role: .close) {}
                }
            },
            message: {
                Text(
                    "Connecte com a internet e tente nvoamente para visualizar o conteudo recentes"
                )
            }
        )
        .onChange(of: homeStore.state.showToastErrorIfNotConnectionInternet){ _,newValue in
            isShowToastError = newValue
            
        }
    }
    
}

#Preview {
    HomeScreen()
}
