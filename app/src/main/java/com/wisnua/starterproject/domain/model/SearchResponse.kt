package com.wisnua.starterproject.domain.model
import com.google.gson.annotations.Expose

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.SerializedName


@Parcelize
data class SearchResponse(
    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean? = null,
    @SerializedName("items")
    @Expose
    var items: List<UserItem?>? = null,
    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null
) : Parcelable

@Parcelize
@Entity(tableName = "users")
data class UserItem(
    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null,
    @SerializedName("events_url")
    @Expose
    var eventsUrl: String? = null,
    @SerializedName("followers_url")
    @Expose
    var followersUrl: String? = null,
    @SerializedName("following_url")
    @Expose
    var followingUrl: String? = null,
    @SerializedName("gists_url")
    @Expose
    var gistsUrl: String? = null,
    @SerializedName("gravatar_id")
    @Expose
    var gravatarId: String? = null,
    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null,
    @SerializedName("id")
    @Expose
    @PrimaryKey var id: Int? = null,
    @SerializedName("login")
    @Expose
    var login: String? = null,
    @SerializedName("node_id")
    @Expose
    var nodeId: String? = null,
    @SerializedName("organizations_url")
    @Expose
    var organizationsUrl: String? = null,
    @SerializedName("received_events_url")
    @Expose
    var receivedEventsUrl: String? = null,
    @SerializedName("repos_url")
    @Expose
    var reposUrl: String? = null,
    @SerializedName("score")
    @Expose
    var score: Double? = null,
    @SerializedName("site_admin")
    @Expose
    var siteAdmin: Boolean? = null,
    @SerializedName("starred_url")
    @Expose
    var starredUrl: String? = null,
    @SerializedName("subscriptions_url")
    @Expose
    var subscriptionsUrl: String? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null,
    @SerializedName("user_view_type")
    @Expose
    var userViewType: String? = null,
    var status: String = "inactive"
) : Parcelable