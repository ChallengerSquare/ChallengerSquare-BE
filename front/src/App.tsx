import './App.css'
import { QueryClient, QueryClientProvider } from 'react-query'
import { RecoilRoot } from 'recoil'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Home from '@/pages/home/Home'
import Competition from '@/pages/competition/Competition'
import CompetitionSearch from '@/pages/competition-search/CompetitionSearch'
import CompetitionDetail from '@/pages/competitiondetail/CompetitionDetail'
import CompetitionResult from '@pages/competition-result/CompetitionResult'
import Auth from '@pages/auth/Auth'
import Signup from '@/pages/signup/Signup'
import CompetitionManage from '@pages/competitionmanage/CompetitionManage'
import CreateCompetition from '@pages/createcompetition/CreateCompetition'
import ModifyPromotion from '@pages/modifycompetition/ModifyPromotion'
import Dashboard from './pages/blockchain/dashboard'
import CreateTeam from './pages/createteam/CreateTeam'
import JoinTeam from './pages/jointeam/JoinTeam'
import TempMyInfo from './pages/mypage/myinfo/MyInfoPage'
import TempAlarm from './pages/mypage/alarm/AlarmPage'
import TempCompetitionList from './pages/mypage/competitionlist/CompetitionListPage'
import TempTeamList from './pages/mypage/teamlist/TeamListPage'
import TempResultList from './pages/mypage/resultlist/ResultListPage'
import TempSetting from './pages/mypage/setting/SettingPage'
import EventStreamManager from './components/EventStreamManager/EventStreamManager'
import ParticipateWindow from './pages/participateWindow/ParticipateWindow'

const queryClient = new QueryClient()

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <EventStreamManager />
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/mypage/myinfo" element={<TempMyInfo />} />
            <Route path="/mypage/alarm" element={<TempAlarm />} />
            <Route path="/mypage/teamlist" element={<TempTeamList />} />
            <Route path="/mypage/competitionlist" element={<TempCompetitionList />} />
            <Route path="/mypage/resultlist" element={<TempResultList />} />
            <Route path="/mypage/setting" element={<TempSetting />} />
            <Route path="/competition" element={<Competition />} />
            <Route path="/competition/search" element={<CompetitionSearch />} />
            <Route path="/competition/detail/:competitionId" element={<CompetitionDetail />} />
            <Route path="/competition/manage/:competitionId" element={<CompetitionManage />} />
            <Route path="/competition/edit/:competitionId" element={<ModifyPromotion />} />
            <Route path="/competition-results" element={<CompetitionResult />} />
            <Route path="/competition-results/:code" element={<CompetitionResult />} />
            <Route path="/auth" element={<Auth />} />
            <Route path="/sign-up" element={<Signup />} />
            <Route path="/create-competition" element={<CreateCompetition />} />
            <Route path="/create-team" element={<CreateTeam />} />
            <Route path="/join/:code" element={<JoinTeam />} />
            <Route path="/form/write/:competitionId" element={<ParticipateWindow />} />
            <Route path="/blockchain/dashboard" element={<Dashboard />} />
            <Route path="*" element={<div>없는 페이지</div>} />
          </Routes>
        </BrowserRouter>
      </RecoilRoot>
    </QueryClientProvider>
  )
}

export default App
