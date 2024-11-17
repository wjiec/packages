//
//  Profile.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import Foundation

struct Profile {
    var username: String
    
    var prefersNotifications = true
    var seasonalPhoto = Season.Winter
    var goalDate = Date()
    
    static let `default` = Profile(username: "Tim Cook")
    
    enum Season: String, CaseIterable {
        case Spring = "🌸"
        case Summer = "🏖️"
        case Autumn = "🍂"
        case Winter = "☃️"
        
        var id: String { rawValue }
    }
}
