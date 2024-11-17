//
//  ProfileEditor.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct ProfileEditor: View {
    @Binding var profile: Profile
    
    var dateRange: ClosedRange<Date> {
        let min = Calendar.current.date(byAdding: .year, value: -1, to: profile.goalDate)!
        let max = Calendar.current.date(byAdding: .year, value: +1, to: profile.goalDate)!

        return min...max
    }
    
    var body: some View {
        List {
            HStack {
                Text("Username")
                Spacer()
                
                // The first control in the view is a TextField, which controls and
                // updates a string binding — in this case, the user’s chosen display
                // name. You provide a label and a binding to a string when creating
                // a text field.
                TextField("Username", text: $profile.username)
                    .foregroundStyle(.secondary)
                    .multilineTextAlignment(.trailing)
            }
            
            // Toggles are controls that are either on or off, so they’re a
            // good fit for Boolean values like a yes or no preference.
            Toggle(isOn: $profile.prefersNotifications) {
                Text("Enable Notifications")
            }
            
            // Place a Picker control and its label in the HStack to make the
            // landmark photos have a selectable preferred season.
            Picker("Season Photo", selection: $profile.seasonalPhoto) {
                ForEach(Profile.Season.allCases, id: \.rawValue) { season in
                    Text(season.rawValue).tag(season)
                }
            }
            
            // Finally, add a DatePicker below the season selector to make the
            // landmark visitation goal date modifiable.
            DatePicker(selection: $profile.goalDate, in: dateRange, displayedComponents: .date) {
                Text("Goal Date")
            }
        }
    }
}

#Preview {
    ProfileEditor(profile: .constant(.default))
}
