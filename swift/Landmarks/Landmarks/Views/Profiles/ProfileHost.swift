//
//  ProfileHost.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import SwiftUI

struct ProfileHost: View {
    // Earlier you used @Environment to retrieve a class that you
    // stored in the environment. Here, you use it to access the
    // editMode value that’s built into the environment to read or
    // write the edit scope.
    @Environment(\.editMode) var editMode
    
    // Read the user’s profile data from the environment to pass
    // control of the data to the profile host.
    @Environment(ModelData.self) var modelData
    
    // To avoid updating the global app state before confirming any
    // edits — such as while the user enters their name — the editing
    // view operates on a copy of itself.
    @State private var draftProfile: Profile = .default

    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            HStack {
                // Unlike the Done button that EditButton provides, the Cancel
                // button doesn’t apply the edits to the real profile data in its closure.
                if editMode?.wrappedValue == .active {
                    Button("Cancel", role: .cancel) {
                        draftProfile = modelData.profile
                        editMode?.animation().wrappedValue = .inactive
                    }
                }
                
                Spacer()
                
                EditButton()
            }
            
            if editMode?.wrappedValue == .inactive {
                ProfileSummary(profile: modelData.profile)
            } else {
                ProfileEditor(profile: $draftProfile)
                    .onAppear {
                        draftProfile = modelData.profile
                    }
                    .onDisappear {
                        modelData.profile = draftProfile
                    }
            }
        }
        .padding()
    }
}

#Preview {
    let modelData = ModelData()
    
    ProfileHost()
        .environment(modelData)
}
