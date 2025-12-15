//
//  FontsApp.swift
//  iosApp
//
//  Created by kenjimaeda on 03/12/25.
//

import SwiftUI

struct FontsApp {
    
    static var titleLarge: Font {
        return .googleSans(size: 17, weight: .bold)
    }

    static var bodyMedium: Font {
        return .googleSans(size: 15, weight: .regular)
    }

    static var bodySmall: Font {
        return .googleSans(size: 14, weight: .thin)
    }
    
    static var headline: Font {
        return .googleSans(size: 20, weight: .bold)
    }
}
