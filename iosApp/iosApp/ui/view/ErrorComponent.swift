//
//  ErrorComponent.swift
//  iosApp
//
//  Created by kenjimaeda on 26/12/25.
//

import SwiftUI

struct ErrorComponent: View {
    var body: some View {
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
    }
}

#Preview {
    ErrorComponent()
}
