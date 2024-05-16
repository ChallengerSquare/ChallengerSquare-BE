import Navbar from '@/components/Navbar/Navbar'
import MyPageTab from '@/pages/mypage/MyPageTab'
import styles from '@/pages/mypage/MyPage.module.scss'
import TeamList from '@/pages/mypage/teamlist/TeamList'

const TempTeamList = () => {
  return (
    <div>
      <Navbar />
      <div className={styles.container}>
        <div className={styles.head}>{'마이페이지'}</div>
        <div className={styles.body}>
          <div className={styles.tap}>
            <MyPageTab tab={'teamList'} />
          </div>
          <div className={styles.content_wrap}>
            <TeamList />
          </div>
        </div>
      </div>
    </div>
  )
}

export default TempTeamList
