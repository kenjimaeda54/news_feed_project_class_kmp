//
//  TabItem.swift
//  iosApp
//
//  Created by kenjimaeda on 13/12/25.
//

enum TabItem: Int,CaseIterable {
   case home
   case article
    
    var title: String {
        switch self {
        case .article: return "Artigos"
        case .home: return "Ínicio"
        }
    }
    
    var iconName: String {
        switch self  {
        case .article: return "document.fill"
        case .home: return "house.fill"
        }
    }
    
}

