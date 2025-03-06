package com.wisnua.starterproject.domain.model
import com.google.gson.annotations.Expose

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.SerializedName


@Parcelize
data class DetailResponse(
    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null,
    @SerializedName("bio")
    @Expose
    var bio: String? = null,
    @SerializedName("blog")
    @Expose
    var blog: String? = null,
    @SerializedName("company")
    @Expose
    var company: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("events_url")
    @Expose
    var eventsUrl: String? = null,
    @SerializedName("followers")
    @Expose
    var followers: Int? = null,
    @SerializedName("followers_url")
    @Expose
    var followersUrl: String? = null,
    @SerializedName("following")
    @Expose
    var following: Int? = null,
    @SerializedName("following_url")
    @Expose
    var followingUrl: String? = null,
    @SerializedName("gists_url")
    @Expose
    var gistsUrl: String? = null,
    @SerializedName("gravatar_id")
    @Expose
    var gravatarId: String? = null,
    @SerializedName("hireable")
    @Expose
    var hireable: String? = null,
    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null,
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("location")
    @Expose
    var location: String? = null,
    @SerializedName("login")
    @Expose
    var login: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("node_id")
    @Expose
    var nodeId: String? = null,
    @SerializedName("organizations_url")
    @Expose
    var organizationsUrl: String? = null,
    @SerializedName("public_gists")
    @Expose
    var publicGists: Int? = null,
    @SerializedName("public_repos")
    @Expose
    var publicRepos: Int? = null,
    @SerializedName("received_events_url")
    @Expose
    var receivedEventsUrl: String? = null,
    @SerializedName("repos_url")
    @Expose
    var reposUrl: String? = null,
    @SerializedName("site_admin")
    @Expose
    var siteAdmin: Boolean? = null,
    @SerializedName("starred_url")
    @Expose
    var starredUrl: String? = null,
    @SerializedName("subscriptions_url")
    @Expose
    var subscriptionsUrl: String? = null,
    @SerializedName("twitter_username")
    @Expose
    var twitterUsername: String? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null,
    @SerializedName("user_view_type")
    @Expose
    var userViewType: String? = null
) : Parcelable