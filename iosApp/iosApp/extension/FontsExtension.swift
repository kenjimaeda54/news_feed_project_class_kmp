//
//  FontsExtension.swift
//  iosApp
//
//  Created by kenjimaeda on 03/12/25.
//

import SwiftUI

struct FontFamilyApp {
    static let familyName = "Google_fonts"
}

extension Font {
    
    static func googleSans(size: CGFloat, weight: Font.Weight) -> Font {
        
        let baseFont = Font.custom(FontFamilyApp.familyName, size: size)
        
        return baseFont.weight(weight)
    }
}
