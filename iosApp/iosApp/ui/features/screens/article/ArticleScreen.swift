//
//  HomeScreen.swift
//  iosApp
//
//  Created by kenjimaeda on 27/11/25.
//

import Shared
import SwiftUI

struct ArticleScreen: View {
    @StateObject private var articleStore = ArticleStore()
    @State private var articleSearch: String = ""
    @State var isShowToastError = false
    @State var isShowAlert = false
    
    var body: some View {
        NavigationStack {
            VStack(spacing: 0) {
                if articleStore.state.isLoading {
                    ZStack {
                        Spacer()
                        ProgressView()
                        Spacer()
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .background(Color(.systemBackground))

                } else if articleStore.state.error != nil {
                    ZStack {
                        Spacer()
                        Text("Erro desconhecido")
                            .font(FontsApp.titleLarge)
                            .foregroundColor(ColorsApp.error)
                            .multilineTextAlignment(.center)
                            .padding(16)
                        Spacer()
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .background(Color(.systemBackground))
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
                                ArticleRow(content: content)
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
            .alert("Sem internet", isPresented: $isShowToastError, actions: {
                Button("Tentar novamente") {
                    articleStore.sendIntent(ArticleIntent.LoadIAllArticles())
                }
                if #available(iOS 26.0, *) {
                    Button("Continuar sem atualizar", role: .close) {}
                }
            }, message: {
               Text("Connecte com a internet e tente nvoamente para visualizar o conteudo recentes")
            })
            .onAppear {
                articleStore.sendIntent(ArticleIntent.LoadIAllArticles())
            }
            .onChange(of: articleStore.state.showToastErrorIfNotConnectionInternet) { _, newValue in
                    isShowToastError = newValue
            }

        }
    }
}

// MARK: - Subview (HomeContent / ArticleRow)

struct ArticleRow: View {
    let content: ContentEntity

    var body: some View {
        HStack(spacing: 10) {
            AsyncImage(url: URL(string: content.urlToImage)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                Rectangle()
                    .fill(ColorsApp.outlineVariant)
            }
            .frame(width: 150, height: 150)
            .clipShape(RoundedRectangle(cornerRadius: 5))

            VStack(alignment: .leading, spacing: 13) {
                Text(content.title)
                    .font(FontsApp.titleLarge)
                    .foregroundColor(ColorsApp.onBackground)

                Text(content.description_)
                    .font(FontsApp.bodyMedium)
                    .foregroundColor(ColorsApp.onBackground)
                    .lineLimit(4)
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 15)
    }
}

// MARK: - Preview

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        ArticleScreen()
    }
}
