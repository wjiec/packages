//
//  ProfileSummary.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct ProfileSummary: View {
    @Environment(ModelData.self) var modelData: ModelData
    var profile: Profile
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 10) {
                Text(profile.username)
                    .font(.title)
                    .bold()
                
                Text("Notifications: \(profile.prefersNotifications ? "On": "Off" )")
                Text("Seasonal Photos: \(profile.seasonalPhoto.rawValue)")
                Text("Goal Date: ") + Text(profile.goalDate, style: .date)
                
                Divider()
                
                VStack(alignment: .leading) {
                    Text("Completed Badge")
                        .font(.headline)
                    
                    ScrollView(.horizontal) {
                        HStack {
                            HikeBadge(name: "First Hike")
                            
                            HikeBadge(name: "Earth Day")
                                .hueRotation(.degrees(90))
                            
                            HikeBadge(name: "Tenth Hike")
                                .grayscale(0.5)
                                .hueRotation(.degrees(45))
                        }
                    }
                }
                
                Divider()
                VStack(alignment: .leading) {
                    Text("Recent Hikes")
                        .font(.headline)
                    
                    HikeView(hike: modelData.hikes[0])
                }
            }
        }
    }
}

#Preview {
    let modelData = ModelData()
    
    ProfileSummary(profile: .default)
        .environment(modelData)
}
