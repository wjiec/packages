//
//  HikeView.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/15.
//

import SwiftUI

struct HikeView: View {
    var hike: Hike
    @State private var showDetail: Bool = false
    
    var body: some View {
        VStack {
            HStack {
                HikeGraph(hike: hike, path: \.elevation)
                    .frame(width: 50, height: 35)
                
                VStack(alignment: .leading) {
                    Text(hike.name)
                        .font(.headline)
                    
                    Text(hike.distanceText)
                        .font(.subheadline)
                        .opacity(0.7)
                }
                
                Spacer()
                
                Button {
                    // Both of the views affected by the showDetail property — the disclosure
                    // button and the HikeDetail view — now have animated transitions.
                    withAnimation(/*.easeInOut(duration: 4)*/) {
                        showDetail.toggle()
                    }
                } label: {
                    Label("Graph", systemImage: "chevron.right.circle")
                        .labelStyle(.iconOnly)
                        .imageScale(.large)
                        .rotationEffect(Angle(degrees: showDetail ? 90 : 0))
                        .padding()
                        .foregroundStyle(.blue)
                        // turn on animation for the button’s rotation by adding an
                        // animation modifier that begins on changes of the showDetail value.
                        //.animation(.easeInOut, value: showDetail)
                }
                .buttonStyle(.plain)
            }
            
            if showDetail {
                // By default, views transition on- and offscreen by fading in and out.
                // You can customize this transition by using the transition(_:) modifier.
                HikeDetail(hike: hike)
                    .transition(.moveAndFade)
            }
        }
    }
}

#Preview {
    let hike = ModelData().hikes[0]

    VStack {
        HikeView(hike: hike)
            .padding()
        
        Spacer()
    }
}
