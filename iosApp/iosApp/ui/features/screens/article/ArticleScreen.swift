//
//  HomeScreen.swift
//  iosApp
//
//  Created by kenjimaeda on 27/11/25.
//

import Shared
import SwiftUI

struct ArticleScreen: View {
    @StateObject private var articleStore = StateFactory.makeArticleStore()
    @State private var articleSearch: String = ""
    @State var isShowToastError = false

    var body: some View {
        NavigationStack {
            VStack(spacing: 0) {
                if articleStore.state.isLoading {
                    LoadingComponent()
                } else if articleStore.state.error != nil
                    && !articleStore.state.showToastErrorIfNotConnectionInternet
                {
                    ErrorComponent()
                } else {
                    VStack(spacing: 0) {
                        TextField(
                            "Digite para pequisar articos",
                            text: $articleSearch
                        )
                        .font(FontsApp.bodySmall)
                        .foregroundColor(ColorsApp.onBackground)
                        .onChange(of: articleSearch) {
                            articleStore.sendIntent(
                                ArticleIntent.SearchArticle(
                                    query: articleSearch
                                )
                            )
                        }
                        .padding(.horizontal, 13)

                        List {
                            ForEach(
                                articleStore.state.articles.content,
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
            }
            .background(ColorsApp.background)
            .edgesIgnoringSafeArea(.bottom)
            .alert(
                "Sem internet",
                isPresented: $isShowToastError,
                actions: {
                    Button("Tentar novamente") {
                        articleStore.sendIntent(
                            ArticleIntent.LoadIAllArticles()
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
            .onAppear {
                articleStore.sendIntent(ArticleIntent.LoadIAllArticles())
            }
            .onChange(
                of: articleStore.state.showToastErrorIfNotConnectionInternet
            ) { _, newValue in
                isShowToastError = newValue
            }

        }
    }
}
