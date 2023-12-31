package com.example.streamwidetechtest.di

import android.app.Application
import androidx.room.Room
import com.example.streamwidetechtest.data.contact_provider.ContactContentProvider
import com.example.streamwidetechtest.data.contact_provider.ContactContentProviderImpl
import com.example.streamwidetechtest.data.local.ContactsDataBase
import com.example.streamwidetechtest.data.local.data_source.ContactsDataSource
import com.example.streamwidetechtest.data.local.data_source.ContactsDataSourceImpl
import com.example.streamwidetechtest.data.repository.ContactsRepositoryImpl
import com.example.streamwidetechtest.domain.repository.ContactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Provides
    @Singleton
    fun provideContactsDB(app: Application): ContactsDataBase {
        return Room.databaseBuilder(
            app,
            ContactsDataBase::class.java,
            ContactsDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactContentProvider(app: Application): ContactContentProvider {
        return ContactContentProviderImpl(app)
    }

    @Provides
    @Singleton
    fun provideDataSource(db: ContactsDataBase): ContactsDataSource {
        return ContactsDataSourceImpl(db.contactsDao)
    }

    @Provides
    @Singleton
    fun provideContactsRepository(dataSource: ContactsDataSource, contactContentProvider: ContactContentProvider): ContactsRepository {
        return ContactsRepositoryImpl(dataSource, contactContentProvider)
    }
}