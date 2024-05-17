import { useState, useEffect } from 'react'
import styles from '@/pages/mypage/alarm/Alarm.module.scss'
import notificationsIcon from '@svgs/notifications.svg'
import { isReadable } from 'stream'
import EmptyImg from '@/components/EmptyImg/EmptyImg'
import { getAlarmList, readAlarm } from '@/services/Alarm'
import { useRecoilState } from 'recoil'
import { notificationState } from '@/stores/notificationState'

interface alarmData {
  alertId: number
  alertType: string
  alertContent: string
  isRead: boolean
  alertTargetId: number
}

const Alarm = () => {
  const [notification, setNotification] = useRecoilState(notificationState)
  const [alramList, setAlarmList] = useState<alarmData[]>([])

  useEffect(() => {
    getAlarmList().then(({ data }) => {
      setAlarmList(data)
    })
  }, [])

  const handleAlarm = (idx: number) => {
    readAlarm(alramList[idx].alertId)
    const updatedAlarms = [...alramList]
    updatedAlarms[idx] = { ...updatedAlarms[idx], isRead: true }
    setAlarmList(updatedAlarms)
    setNotification(false)
  }

  return (
    <div className={styles.info}>
      <div className={styles.title}>
        <div>{'HOME > 알림'}</div>
      </div>
      {alramList.length > 0 ? (
        <div className={styles.content}>
          {alramList.map((alarm, index) => (
            <button
              key={alarm.alertId}
              type={'button'}
              className={`${styles.notification} ${alarm.isRead ? '' : styles.unread}`}
              disabled={alarm.isRead}
              onClick={() => handleAlarm(index)}
            >
              <div>
                <div className={styles.notice_title}>
                  <div className={`${styles.point} ${alarm.isRead ? '' : styles.unread}`}>{''}</div>
                  <div>{'SSAFY 자율 프로젝트'}</div>
                </div>
                <div className={styles.notice_content}>{alarm.alertContent}</div>
              </div>
              <div className={styles.time}>
                <img src={notificationsIcon} alt={'notificationsIcon'} />
                {'1시간 전'}
              </div>
            </button>
          ))}
        </div>
      ) : (
        <div className={styles.empty}>
          <EmptyImg text={'알림이 없습니다.'} />
        </div>
      )}
    </div>
  )
}

export default Alarm
