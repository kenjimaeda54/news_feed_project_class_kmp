//
//  CardComponent.swift
//  iosApp
//
//  Created by kenjimaeda on 26/12/25.
//

import SwiftUI
import Shared

struct CardComponent: View {
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
                if(!content.title.isEmpty) {
                    Text(content.title)
                    .font(FontsApp.titleLarge)
                    .foregroundColor(ColorsApp.onBackground)

                }
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

