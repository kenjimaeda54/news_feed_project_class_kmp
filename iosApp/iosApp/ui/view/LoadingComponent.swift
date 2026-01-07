//
//  LoadingComponent.swift
//  iosApp
//
//  Created by kenjimaeda on 26/12/25.
//

import SwiftUI

struct LoadingComponent: View {
    var body: some View {
        ZStack {
            Spacer()
            ProgressView()
            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(.systemBackground))
    }
}

#Preview {
    LoadingComponent()
}
