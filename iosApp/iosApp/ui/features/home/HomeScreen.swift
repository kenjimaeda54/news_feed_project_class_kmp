//
//  HomeScreen.swift
//  iosApp
//
//  Created by kenjimaeda on 27/11/25.
//

import SwiftUI
import Shared

struct HomeScreen: View {
    @StateObject private var homeStore = HomeStore()
    @State private var articleSearch: String = ""
    
    var body: some View {
        VStack(spacing: 0) {
            if homeStore.state.isLoading {
                ZStack {
                    Spacer()
                    ProgressView()
                    Spacer()
                }
                .frame(maxWidth: .infinity,maxHeight: .infinity)
                .background(Color(.systemBackground))
                
            } else if homeStore.state.error != nil {
                ZStack {
                    Spacer()
                    Text("Erro desconhecido")
                        .font(FontsApp.titleLarge)
                        .foregroundColor(ColorsApp.error)
                        .multilineTextAlignment(.center)
                        .padding(16)
                    Spacer()
                }
                .frame(maxWidth: .infinity,maxHeight: .infinity)
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
                        //homeStore.searchArticles(query: articleSearch)
                    }
                    .padding(.horizontal,13)
                    
                    List {
                        ForEach(homeStore.state.currentNews.articles,id: \.url) { article in
                            ArticleRow(article: article)
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
    }
}

// MARK: - Subview (HomeContent / ArticleRow)

struct ArticleRow: View {
    let article: ArticleEntity
    
    var body: some View {
        HStack(spacing: 10) {
            AsyncImage(url: URL(string: article.urlToImage)) { image in
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
                Text(article.title)
                    .font(FontsApp.titleLarge)
                    .foregroundColor(ColorsApp.onBackground)
                
                Text(article.description_)
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
        HomeScreen()
    }
}
