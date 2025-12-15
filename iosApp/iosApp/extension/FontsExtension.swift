//
//  FontsExtension.swift
//  iosApp
//
//  Created by kenjimaeda on 03/12/25.
//

import SwiftUI



extension Font {
    
    private static func fontName(for weight: Font.Weight) -> String  {
        switch weight  {
        case .thin: return "GoogleSans-Thin"
        case .bold: return "GoogleSans-Bold"
        case .medium: return "GoogleSans-Medium"
        default: return "GoogleSans-Regular"
        }
        
    }
    
    static func googleSans(size: CGFloat, weight: Font.Weight) -> Font {
        let name = fontName(for: weight)
        return Font.custom(name, size: size)
    }
}
